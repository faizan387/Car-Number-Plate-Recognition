# **Car Number Plate Recognition System for Punjab Province using Convolutional Neural Networks (CNN)**

Implementation of a Convolutional Neural Network for car number plate datection and recognition. The entire process is divided in four steps and explanation is given for each step to show understanding of the entire process.

## Usage
Code can be accessed through the provided Jupyter Notebook [file](https://github.com/faizan387/Car-Number-Plate-Recognition/blob/master/Car%20plate%20recognition%20Punjab/car%20plate.ipynb)

## Dependencies
* Keras
* OpenCV
* Matplotlibb
* Numpy
* Tensorflow
* Pandas
* Seaborn

## Working

### **Step 1: Pre-process the Car Number Plate**

The image of car number plate is given input and it is preprocessed. In the function **segment_characters** the image is first resized to make all characters and numbers seem distinct and clear. Next it is converted to gray scale image to avoid RGB colors as they are complex and in grayscale values are between 0 – 255. Now to avoid complexity gray scaled image is converted to binary image by applying threshold. Pixel value above 200 are given value 1 and below 200 are given 0. Eroding is applied to each pixel and checked if all neighbor pixels have value 1 then give that pixel value else give 0. It is done to remove unwanted pixel values from image boundary. Then image is Dilated i.e. a pixel is given value 1 if any one of neighbor pixel has value of 1. Borders of image are made white. At last, four dimensions are defined which are the most important in this entire process and are used to extract characters by comparing width and height of each character and number. At the end of this function we have a processed binary image and we pass it to function to extract characters and numbers.

<p align="center">
  <img width="253" alt="a" src="https://user-images.githubusercontent.com/54681019/122272865-459d1380-cefa-11eb-91c9-089c33e263a1.PNG">
</p>
  
Next, we find contours from the binary image in **find_contours** function. The built-in function from OpenCV library is used for this purpose which returns all possible contours. We check every contour whether it contains a character or a number in it. If yes, we save it else, we ignore that contour. We check by comparing the width and height of each contour with the width and height that a character or number could possibly have and it is decided according to Punjab Province.

<p align="center">
  <img width="248" alt="a" src="https://user-images.githubusercontent.com/54681019/122273128-86952800-cefa-11eb-97f5-d3c8be453636.PNG">
</p>

If that contour satisfies the dimensions then it is a contour of character or a number and that contour is extracted, pre-processed and saved in an array. The binary image array of characters and is returned from this function. The individual extracted numbers and characters are returned in an array.

<p align="center">
  <img width="170" alt="a" src="https://user-images.githubusercontent.com/54681019/122273217-a0366f80-cefa-11eb-8de2-853f55257d09.PNG">
</p>

### **Step 2: Create a Machine Learning Model**

The CNN model consists of a number of layers. Its architecture is explained following. First a sequential object is created. First layer is convolutional layer with 32 filters and kernel size (5, 5) and activation function relu. A max pooling layer with pool size (2, 2) is added. Dropout of 0.4 is used to avoid overfitting. In this process, neural network is prevented from overfitting and 40% of neurons will be dropped. Flatten layer is added to flatten the node data. Then we add a dense layer with 128 neurons and activation function relu. Add final layer, which is a dense layer having 36 outputs and activation function softmax. Note that the 36 neurons in final dense layer are because of total number of outputs i.e. 26 alphabets and 10 numbers. The loss function is categorical_crossentropy, optimization function of Adam which takes learning rate 0.00001 and Accuracy as error matrix.

### **Step 3: Train the CNN model**

The data to train consists of 26 alphabets and 10 numbers. So total classes will be 36. The data is size 1100 in which 860 images are for training and 240 are used for testing purpose. Each image of alphabet and number is 28x28. Data for training is 80% and testing has 20%. A class ImageDataGenerator in Keras will be used generate some more data by technique like width shift and height shift. So, after splitting data in training and validation, it is then trained. It is given the training data and validation data and trained for 30 epochs. The accuracy is 88% of the trained model.

### **Step 4: Testing the CNN Model**

Now the binary images which were extracted and returned in array will be given to the mode 1 by 1 to identify and then test if it predicted correct. The function show_results predicts every image of character and number in the array of extracted contours. It then returns the predicted output by model. The model is making predictions with **88% accuracy**.

<p align="center">
  <img width="470" alt="a" src="https://user-images.githubusercontent.com/54681019/122273346-c52ae280-cefa-11eb-83a3-6804055c43e1.PNG">
<p align="center">
