#include <iostream>
#include <opencv2/opencv.hpp>

using namespace cv;

int main(int argc, char** argv) {
    if (argc != 2) {
    	std::cout << "No input file " << std::endl;
        return -1;
    }
    Mat image;
    image = imread(argv[1], 1);
    if (!image.data) {
        std::cout << "No image data" << std::endl;
        return -1;
    }

    Mat grayImage;
    cvtColor(image, grayImage, COLOR_BGR2GRAY);
    imwrite("./Gray_Image.jpg", grayImage);

    return 0;
}
