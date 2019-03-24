TARGET = demo-mmcif
TEMPLATE = app
DEPENDPATH += .
INCLUDEPATH += . 
CONFIG -= qt

MMCIF=/
QMAKE_CXXFLAGS += -std=c++11 -I$${MMCIF}/include
LIBS += $${MMCIF}/lib/all.a
	
SOURCES += main.cpp
	
OBJECTS_DIR = .build/obj
MOC_DIR     = .build/moc
RCC_DIR     = .build/rcc
UI_DIR      = .build/ui

