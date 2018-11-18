TEMPLATE = app
TARGET = cpp-opencv
DEPENDPATH += .
INCLUDEPATH += .

QT -= core gui
CONFIG -= qt

QMAKE_CXXFLAGS += -std=c++11

OpenCV=/
QMAKE_CXXFLAGS += -I$${OpenCV}/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/core/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/imgproc/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/highgui/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/ml/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/video/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/features2d/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/calib3d/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/objdetect/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/flann/include
QMAKE_CXXFLAGS += -I$${OpenCV}/modules/contrib/include

LIBS += -L$${OpenCV}/build/lib
LIBS += -lopencv_core -lopencv_imgproc -lopencv_highgui -lopencv_ml -lopencv_video \
-lopencv_features2d -lopencv_calib3d -lopencv_objdetect \
-lopencv_flann -lopencv_contrib
  
SOURCES += main.cpp

OBJECTS_DIR = .build/obj
MOC_DIR     = .build/moc
RCC_DIR     = .build/rcc
UI_DIR      = .build/ui

