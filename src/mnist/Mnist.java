package mnist;

import fullyconnectednetwork.Network;
import fullyconnectednetwork.NetworkTools;
import trainset.TrainSet;

import java.io.File;

/**
 * Created by Luecx on 10.08.2017.
 */
public class Mnist {

    public static void main(String[] args) {

        try {
            Network net = Network.loadNetwork("res/mnist1.txt");
            testTrainSet(net, createTrainSet(1000,2000), 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static TrainSet createTrainSet(int start, int end) {

        TrainSet set = new TrainSet(28 * 28, 10);

        try {

            String path = new File("").getAbsolutePath();

            MnistImageFile m = new MnistImageFile(path + "/res/trainImage.idx3-ubyte", "rw");
            MnistLabelFile l = new MnistLabelFile(path + "/res/trainLabel.idx1-ubyte", "rw");

            for(int i = start; i <= end; i++) {
                if(i % 100 ==  0){
                    System.out.println("prepared: " + i);
                }

                double[] input = new double[28 * 28];
                double[] output = new double[10];

                output[l.readLabel()] = 1d;
                for(int j = 0; j < 28*28; j++){
                    input[j] = (double)m.read() / (double)256;
                }

                set.addData(input, output);
                m.next();
                l.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

         return set;
    }

    public static void trainData(Network net,TrainSet set, int epochs, int loops, int batch_size, String output_file) {
        for(int e = 0; e < epochs;e++) {
            net.train(set, loops, batch_size);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>   "+ e+ "   <<<<<<<<<<<<<<<<<<<<<<<<<<");
            try {
                net.saveNetwork(output_file);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void testTrainSet(Network net, TrainSet set, int printSteps) {
        int correct = 0;
        for(int i = 0; i < set.size(); i++) {

            double highest = NetworkTools.indexOfHighestValue(net.calculate(set.getInput(i)));
            double actualHighest = NetworkTools.indexOfHighestValue(set.getOutput(i));
            if(highest == actualHighest) {
                correct ++ ;
            }
            if(i % printSteps == 0) {
                System.out.println(i + ": " + (double)correct / (double) (i + 1));
            }
        }
        System.out.println("Testing finished, RESULT: " + correct + " / " + set.size()+ "  -> " + (double)correct / (double)set.size() +" %");
    }
}
