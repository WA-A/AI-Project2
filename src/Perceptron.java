import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Perceptron {
    private double[] weights;
    private double learningRate;
    private double accuracy;
    private static int threshold=60;
    private double mse=0;
    private double per=0;
    private static List<Double[]> data = new ArrayList<>();
    public static List<Double> labels = new ArrayList<>();

    public Perceptron(double learningRate, int numberOfFeatures) {
        this.learningRate = learningRate;
        this.weights = new double[numberOfFeatures + 1]; // +1 for the bias
        initializeWeights();
    }


    public double getMSE() {
        return mse;
    }

    private void initializeWeights() {

//        for (int i = 0; i < weights.length; i++) {
//            weights[i] = Math.random() * 0.01;
//
//        }
        weights[0]=.1;
        weights[1]= -.1;
        weights[2]=.1;
        weights[3]=.1;
    }


    public boolean train(List<Double[]> data, int epochs, double goalAccuracy) {
        int correctCount;

        for (int epoch = 1; epoch <= epochs; epoch++) {
            correctCount = 0;
           mse = 0;
            for (int i = 0; i < data.size(); i++) {
                Double[] features = data.get(i);
                double label = labels.get(i);  // Get label from labels list
                double predicted = predict(features);
                double error=label-predicted;
                if (error==0) {
                    correctCount++;
                } else {
                    mse += error*error;
                    updateWeights(features, error);
                }
            }

            mse = mse/ (double) data.size();
            per = (mse);
            accuracy = (correctCount / (double) data.size());

            System.out.println("Epoch " + epoch + ": Accuracy = " + accuracy);
            System.out.println("Epoch " + epoch + ": MSE = " + mse);

            if (accuracy >= goalAccuracy) {
                System.out.println("Training completed successfully in epoch " + epoch + " with accuracy " + String.format("%.3f%%", accuracy*100));
                return true;
            }
        }

        System.out.println("Training not completed. Reached accuracy: " + String.format("%.3f%%", accuracy*100));
        return false;
    }


    public boolean test(List<Double[]> data) {
        int correctCount;
            correctCount = 0;
            mse = 0;

            for (int i = 0; i < data.size(); i++) {
                Double[] features = data.get(i);
                double label = labels.get(i);  // Get label from labels list
                double predicted = predict(features);
                double error=label-predicted;
                if (error==0) {
                    correctCount++;
                } else {
                    mse += error*error;
                }
            }

            mse = mse/ (double) data.size();
            per = (mse);
            accuracy = (correctCount / (double) data.size());

            System.out.println(": Accuracy = " + accuracy);
            System.out.println(": MSE = " + mse);

        System.out.println("Testing not completed. Reached accuracy: " + String.format("%.3f%%", accuracy*100));
        return false;
    }

public double getPerformance(){
        return this.per;
}
    private double predict(Double[] features) {
        double sum = weights[0]; // (weights[0] is the bias)
        for (int i = 1; i < features.length + 1; i++) { // Ensure the loop iterates over the features length
            if (features[i - 1] >= threshold) {
                sum += weights[i] * 1;
            } else {
                sum += weights[i] * 0;
            }
        }
        return sum >= 0 ? 1.0 : 0.0;
    }



//    private double predictTest(Double[] features) {
//        double sum = weights[0]; // (weights[0] is the bias)
//        for (int i = 1; i < weights.length; i++) {
//            if (features[i - 1] >= threshold) {
//                sum += weights[i] * 1;
//            } else {
//                sum += weights[i] * 0;
//            }
//        }
//        return sum >= 0 ? 1.0 : 0.0;
//    }

    private void updateWeights(Double[] features, double error) {
        double err = error;
        for (int i = 1; i < features.length + 1; i++) { // Ensure the loop iterates over the features length
            if (features[i - 1] >= threshold) {
                weights[i] += learningRate * err * 1;
            } else {
                weights[i] += learningRate * err * 0;
            }
        }
    }


    public double getAccuracy() {
        return accuracy*100;
    }

    public static List<Double[]> parseData(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.replaceAll("[{}]", "").split(",");
            Double[] inputs = new Double[tokens.length];
            double sum = 0;
            for (int i = 0; i < tokens.length; i++) {
                inputs[i] = Double.parseDouble(tokens[i]);
                sum += inputs[i];
            }
            double average = sum / tokens.length;
            data.add(inputs);
            labels.add(average >= threshold ? 1.0 : 0.0);
        }
        scanner.close();



        System.out.println("Data size: " + data.size());
        System.out.println("Labels size: " + labels.size());
        if (data.size() > 0) {
            System.out.println("Features size: " + data.get(0).length);
        }
        return data;
    }

}