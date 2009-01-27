// Copyright 2009 Information Without Borders
// 
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

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
		action_create,
		action_show,
		action_update,
		action_destroy,
		action_index,
		action_fail,
		action_none
	} action = action_none;
	enum {
		resource_interests,
		resource_data_objects,
		resource_none
	} resource = resource_none;
	char *action_parameter = NULL;
	char *resource_parameter = NULL;
	char *attr_name = NULL;
	char *attr_value = NULL;
	long attr_weight = 1;
	
	// Parse action-line arguments:
	for(i = 1; i < argc; i++)
	{
		if(strcmp(argv[i], "-p") == 0)
		{
			i++;
			progname = argv[i];
		}
		
		else if(action == action_none && strcmp(argv[i], "create") == 0)
		{
			action = action_create;
			i++;
			if(i < argc)
				action_parameter = argv[i];
		}
		else if(action == action_none && strcmp(argv[i], "show") == 0)
		{
			action = action_show;
			i++;
			if(i < argc)
				action_parameter = argv[i];
		}
		else if(action == action_none && strcmp(argv[i], "update") == 0)
		{
			action = action_update;
			i++;
			if(i < argc)
				action_parameter = argv[i];
		}
		else if(action == action_none && strcmp(argv[i], "destroy") == 0)
		{
			action = action_destroy;
			i++;
			if(i < argc)
				action_parameter = argv[i];
		}
		else if(action == action_none && strcmp(argv[i], "index") == 0)
		{
			action = action_index;
		}

		else if(resource == resource_none && strcmp(argv[i], "interests") == 0)
		{
			resource = resource_interests;
			i++;
			if(i < argc)
				resource_parameter = argv[i];
		}
		else if(action == action_none && strcmp(argv[i], "data") == 0)
		{
			resource = resource_data_objects;
			i++;
			if(i < argc)
				resource_parameter = argv[i];
		}
		
		else
		{
			printf("Unrecognized parameter: %s\n", argv[i]);
			action = action_fail;
		}
	}
	
	if(progname == NULL)
	{
		progname = (char *) "sneakernet";
	}
	
	switch(action)
	{
		case action_create:
		case action_destroy:
		case action_update:
			// Parse the argument:
			if(action_parameter != NULL)
			{
				attr_name = action_parameter;
				attr_value = action_parameter;
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
		case action_none:
		case action_fail:
			printf(
"\n"
"Usage:\n"
"driver [-p <name of program>] ACTION <attribute>\n"
"\n"
"-p  Set the program name. (optional)\n"
"\n"
"    Actions are:\n"
"      create   Creates a new resource item and returns an XML representation of it.\n"
"      show     Show an XML representation of the requested object.\n"
"      update   Update the specified object using the provided XML representation.\n"
"      destroy  Destroy the specified object.\n"
"      index    Get a list of all items of the specified resource."
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
	
	switch(action)
	{
		case action_create:
			// Add interest:
			haggle_ipc_add_application_interest_weighted(
				haggle_, 
				attr_name, 
				attr_value,
				attr_weight);
		break;
		
		case action_destroy:
			// Remove interest:
			haggle_ipc_remove_application_interest(
				haggle_,
				attr_name, 
				attr_value);
		break;
		
		case action_update:
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
		
		case action_index:
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
