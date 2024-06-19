import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class GUI extends JFrame {
    JTextField learningRateField, epochsField, goalAccuracyField;
    JButton trainButton, testButton, selectTrainDataButton, selectTestDataButton;
    JLabel trainResultsLabel, testResultsLabel, mseTrainLabel,mseTestLabel;
    JFileChooser fileChooser;
    Perceptron perceptron;
    File trainDataFile;
    File testDataFile;

    public GUI() {
        super("Perceptron Training & Testing GUI");
       //setBackground(new Color(246, 182, 182));
        setLayout(new GridLayout(8, 4, 10, 10));

        setSize(800, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        learningRateField = new JTextField("0.01", 10);
        epochsField = new JTextField("100", 10);
        goalAccuracyField = new JTextField("0.85", 10);


        trainButton = new JButton("Train Model");
        trainButton.setBackground(new Color(246, 182, 182));
        trainButton.setFont(new Font("Serif", Font.ITALIC, 20));

        testButton = new JButton("Test Model");
        testButton.setBackground(new Color(246, 182, 182));
        testButton.setFont(new Font("Serif", Font.ITALIC, 20));

        selectTrainDataButton = new JButton("Select Training Data");
        selectTrainDataButton.setBackground(new Color(246, 182, 182));
        selectTrainDataButton.setFont(new Font("Serif", Font.ITALIC, 20));


        selectTestDataButton = new JButton("Select Testing Data");
        selectTestDataButton.setBackground(new Color(246, 182, 182));
        selectTestDataButton.setFont(new Font("Serif", Font.ITALIC, 20));


        trainResultsLabel = new JLabel("Training Results: ");
        trainResultsLabel.setFont(new Font("Serif", Font.ITALIC, 20));

        testResultsLabel = new JLabel("Testing Results: ");
        testResultsLabel.setFont(new Font("Serif", Font.ITALIC, 20));


        mseTrainLabel = new JLabel("Mean Squared Error: ");
        mseTrainLabel.setFont(new Font("Serif", Font.ITALIC, 20));


        mseTestLabel = new JLabel("Mean Squared Error: ");
        mseTestLabel.setFont(new Font("Serif", Font.ITALIC, 20));


        fileChooser = new JFileChooser();

        JLabel learningRateLabel = new JLabel("Learning Rate:");
        learningRateLabel.setForeground(new Color(0, 0, 128));
        learningRateLabel.setFont(new Font("Serif", Font.ITALIC, 20));
        add(learningRateLabel);
        add(learningRateField).setFont(new Font("", Font.ITALIC, 20));


        JLabel NumberofEpochs = new JLabel("Number of Epochs:");
        NumberofEpochs.setForeground(new Color(0, 0, 128));
        NumberofEpochs.setFont(new Font("Serif", Font.ITALIC, 20));
        add(NumberofEpochs);
        add(epochsField).setFont(new Font("Serif", Font.ITALIC, 20));

        JLabel GoalAccuracy = new JLabel("Goal Accuracy:");
        GoalAccuracy.setForeground(new Color(0, 0, 128));
        GoalAccuracy.setFont(new Font("Serif", Font.ITALIC, 20));
        add(GoalAccuracy);
        add(goalAccuracyField).setFont(new Font("Serif", Font.ITALIC, 20));

        add(selectTrainDataButton).setForeground(new Color(0, 0, 128));
        add(trainButton).setForeground(new Color(0, 0, 128));
        add(trainResultsLabel).setForeground(new Color(0, 0, 128));
        add(mseTrainLabel).setForeground(new Color(0, 0, 128)); // Added MSE TRAIN label
        add(selectTestDataButton).setForeground(new Color(0, 0, 128));
        add(testButton).setForeground(new Color(0, 0, 128));
        add(testResultsLabel).setForeground(new Color(0, 0, 128));
        add(mseTestLabel).setForeground(new Color(0, 0, 128)); // Added MSE TEST label

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
                mseTrainLabel.setText("Performance: " + String.format("%.3f", perceptron.getPerformance()));

            } catch (Exception ex) {
                trainResultsLabel.setText("Error: " + ex.getMessage());
            }
        });


        // Testing Data


        selectTestDataButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(GUI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                testDataFile = fileChooser.getSelectedFile();
                testResultsLabel.setText("File selected: " + testDataFile.getName());
            }
        });



        testButton.addActionListener(e -> {
            try {
                perceptron = new Perceptron(Double.parseDouble(learningRateField.getText()), numberOfFeatures);
                List<Double[]> data = Perceptron.parseData(testDataFile);
                boolean success = perceptron.test(data);
                String result = success ? "Accuracy: " + String.format("%.3f%%", perceptron.getAccuracy()) : "Accuracy: " + String.format("%.3f%%", perceptron.getAccuracy());
                testResultsLabel.setText("Testing Completed. " + result);
                mseTestLabel.setText("Performance: " + String.format("%.3f", perceptron.getPerformance()));

            }
            catch (Exception ex) {
                testResultsLabel.setText("Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

}
