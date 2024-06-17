import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class GUI extends JFrame {
    JTextField learningRateField, epochsField, goalAccuracyField;
    JButton trainButton, testButton, selectTrainDataButton, selectTestDataButton;
    JLabel trainResultsLabel, testResultsLabel, mseLabel;
    JFileChooser fileChooser;
    Perceptron perceptron;
    File trainDataFile;

    public GUI() {
        super("Perceptron Training GUI");
        setLayout(new GridLayout(5, 2, 10, 10)); // Example grid layout
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        learningRateField = new JTextField("0.01", 10);
        epochsField = new JTextField("100", 10);
        goalAccuracyField = new JTextField("0.85", 10);

        trainButton = new JButton("Train Model");
        testButton = new JButton("Test Model");
        selectTrainDataButton = new JButton("Select Training Data");
        selectTestDataButton = new JButton("Select Testing Data");

        trainResultsLabel = new JLabel("Training Results: ");
        testResultsLabel = new JLabel("Testing Results: ");
        mseLabel = new JLabel("Mean Squared Error: ");

        fileChooser = new JFileChooser();

        add(new JLabel("Learning Rate:"));
        add(learningRateField);
        add(new JLabel("Number of Epochs:"));
        add(epochsField);
        add(new JLabel("Goal Accuracy:"));
        add(goalAccuracyField);

        add(selectTrainDataButton);
        add(trainButton);
        add(trainResultsLabel);
        add(mseLabel); // Added MSE label

        add(selectTestDataButton);
        add(testButton);
        add(testResultsLabel);

        int numberOfFeatures = 3;


        selectTrainDataButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(GUI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                trainDataFile = fileChooser.getSelectedFile();
                trainResultsLabel.setText("File selected: " + trainDataFile.getName());
            }
        });

        trainButton.addActionListener(e -> {
            try {
                perceptron = new Perceptron(Double.parseDouble(learningRateField.getText()), numberOfFeatures);
                List<Double[]> data = Perceptron.parseData(trainDataFile);
                int epochs = Integer.parseInt(epochsField.getText());
                double goalAccuracy = Double.parseDouble(goalAccuracyField.getText());
                boolean success = perceptron.train(data, epochs, goalAccuracy);
                String result = success ? "Goal Reached! Accuracy: " + String.format("%.3f%%", perceptron.getAccuracy()) : "Goal Not Reached. Accuracy: " + String.format("%.3f%%", perceptron.getAccuracy());
                trainResultsLabel.setText("Training Completed. " + result);
                mseLabel.setText("Performance: " + String.format("%.3f", perceptron.getPerformance()));

            } catch (Exception ex) {
                trainResultsLabel.setText("Error: " + ex.getMessage());
            }
        });


        // Similar implementation for testButton and selectTestDataButton

        setVisible(true);
    }

}
