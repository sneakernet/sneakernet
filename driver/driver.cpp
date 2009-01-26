/* Copyright 2008 Uppsala University
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *     
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

#include <libhaggle/haggle.h>

#include <string.h>
#ifndef OS_WINDOWS
#include <signal.h>
#endif

#include <stdio.h>

int main(int argc, char *argv[])
{
	int retval = 1;
	long i;
	haggle_handle_t haggle_;
	char *progname = NULL;
	enum {
		command_add_interest,
		command_delete_interest,
		command_get_interests,
		command_new_dataobject,
		command_fail,
		command_none
	} command = command_none;
	char *command_parameter = NULL;
	char *attr_name = NULL;
	char *attr_value = NULL;
	long attr_weight = 1;
	
	// Parse command-line arguments:
	for(i = 1; i < argc; i++)
	{
		if(strcmp(argv[i], "-p") == 0)
		{
			i++;
			progname = argv[i];
		}else if(command == command_none && strcmp(argv[i], "add") == 0)
		{
			command = command_add_interest;
			i++;
			if(i < argc)
				command_parameter = argv[i];
		}else if(command == command_none && strcmp(argv[i], "del") == 0)
		{
			command = command_delete_interest;
			i++;
			if(i < argc)
				command_parameter = argv[i];
		}else if(command == command_none && strcmp(argv[i], "new") == 0)
		{
			command = command_new_dataobject;
			i++;
			if(i < argc)
				command_parameter = argv[i];
		}else if(command == command_none && strcmp(argv[i], "get") == 0)
		{
			command = command_get_interests;
		}else{
			printf("Unrecognized parameter: %s\n", argv[i]);
			command = command_fail;
		}
	}
	
	if(progname == NULL)
	{
		progname = (char *) "Haggle command line tool";
	}
	
	switch(command)
	{
		case command_add_interest:
		case command_delete_interest:
		case command_new_dataobject:
			// Parse the argument:
			if(command_parameter != NULL)
			{
				attr_name = command_parameter;
				attr_value = command_parameter;
				i = 0;
				while(attr_name[i] != '=' && attr_name[i] != '\0')
					i++;
				if(attr_name[i] != '\0')
				{
					attr_name[i] = '\0';
					i++;
					
					attr_value = &(attr_name[i]);
					i = 0;
					while(attr_value[i] != ':' && attr_value[i] != '\0')
						i++;
					if(attr_value[i] == ':')
					{
						attr_value[i] = '\0';
						i++;
						attr_weight = atol(&(attr_value[i]));
					}
		// Only break out of this switch statement if there was an argument,
		// and it has been correctly parsed:
		break;
				}
			}
			printf("Unable to parse attribute.\n");
			// Intentional fall-through:
		case command_none:
		case command_fail:
			printf(
"\n"
"Usage:\n"
"clitool [-p <name of program>] add <attribute>\n"
"clitool [-p <name of program>] del <attribute>\n"
"clitool [-p <name of program>] new <attribute>\n"
"clitool [-p <name of program>] get\n"
"\n"
"-p  Allows this program to masquerade as another.\n"
"add Tries to add <attribute> to the list of interests for this application.\n"
"del Tries to remove <attribute> from the list of interests for this\n"
"    application.\n"
"new Creates and publishes a new data object with the given attribute\n"
"get Tries to retrieve all interests for this application.\n"
"\n"
"Attributes are specified as such: <name>=<value>[:<weight>]. Name and value\n"
"are text strings, and weight is an optional integer. Name can of course not\n"
"include an '=' character.\n");
			
			return 1;
		break;
		
		default:
		break;
	}
	
	set_trace_level(1);
	
	// Find Haggle:
	if(haggle_handle_get(progname, &haggle_) != haggle_no_error)
		goto fail_haggle;

	printf("Connected to Haggle as: %s\n", progname);
	
	switch(command)
	{
		case command_add_interest:
			// Add interest:
			haggle_ipc_add_application_interest_weighted(
				haggle_, 
				attr_name, 
				attr_value,
				attr_weight);
		break;
		
		case command_delete_interest:
			// Remove interest:
			haggle_ipc_remove_application_interest(
				haggle_,
				attr_name, 
				attr_value);
		break;
		
		case command_new_dataobject:
		{
			struct dataobject *dObj;
			
			// New data object:
			dObj = haggle_dataobject_new();
			
			// Add attribute:
			haggle_dataobject_add_attribute(
				dObj, 
				attr_name, 
				attr_value);
			
			// Publish:
			haggle_ipc_publish_dataobject(haggle_, dObj);
		}
		break;
		
		case command_get_interests:
		{
			struct attributelist *al;
			bool not_done;
			
			// Get interests:
			haggle_ipc_get_application_interests_sync(haggle_, &al);
			
			// Loop through and list interests:
			not_done = true;
			i = 0;
			while(not_done)
			{
				attribute	*a;
				
				a = haggle_attributelist_get_attribute_n(al, i);
				if(a == NULL)
				{
					not_done = false;
				}else{
					printf("Interest: %s=%s:%ld\n",
						haggle_attribute_get_name(a),
						haggle_attribute_get_value(a),
						haggle_attribute_get_weight(a));
					i++;
				}
			}
		}
		break;
		
		// Shouldn't be able to get here:
		default:
		break;
	}

	retval = 0;
	
	// Release the haggle handle:
	haggle_handle_free(haggle_);
fail_haggle:
	return retval;
}
