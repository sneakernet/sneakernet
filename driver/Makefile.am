bin_PROGRAMS=driver
driver_SOURCES=driver.cpp

CPPFLAGS += -I$(top_builddir)/src/libhaggle/include 
CPPFLAGS += -I$(top_builddir)/src/utils 
CPPFLAGS += -I$(top_builddir)/src/libcpphaggle
LDFLAGS += -lhaggle -L$(top_builddir)/src/libhaggle/
LDFLAGS += -lhaggleutils -L$(top_builddir)/src/utils/
LDFLAGS += -lcpphaggle -L$(top_builddir)/src/libcpphaggle

if OS_LINUX
LDFLAGS += -lpthread
endif
if OS_MACOSX
LDFLAGS += -framework CoreServices
endif
CFLAGS += -std=gnu99

all-local:

clean-local:
	rm -f *~
