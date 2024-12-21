import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ElevatorGUI extends JFrame {
    private final ElevatorSystem system;

    public ElevatorGUI(ElevatorSystem system) {
        this.system = system;
        setTitle("Elevator Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setupGUI();
    }

    private void setupGUI() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 5, 5)); // панель для кнопок вызова этажей
        JPanel elevatorPanel = new JPanel(new GridLayout(system.getElevators().size(), 1)); // положение лифтов

        JLabel[] elevatorDisplays = new JLabel[system.getElevators().size()];

        // кнопки для вызова этажей
        for (int i = 1; i <= 10; i++) {
            int floor = i;
            JButton callButton = new JButton("Floor " + floor);
            callButton.addActionListener(e -> system.requestElevator(floor));
            buttonPanel.add(callButton);
        }

        // отображение состояния лифтов
        for (int i = 0; i < elevatorDisplays.length; i++) {
            elevatorDisplays[i] = new JLabel("Elevator " + (i + 1) + " at floor: 1");
            elevatorPanel.add(elevatorDisplays[i]);
        }

        // кнопка сброса
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            system.resetAll();
            for (JLabel display : elevatorDisplays) {
                display.setText("Elevator " + (display.getText().split(" ")[1]) + " reset to floor: 1");
            }
        });

        // обновление состояния лифтов
        new Timer(1000, e -> updateElevatorPositions(elevatorDisplays)).start();

        add(buttonPanel, BorderLayout.CENTER);
        add(elevatorPanel, BorderLayout.EAST);
        add(resetButton, BorderLayout.SOUTH);
    }

    private void updateElevatorPositions(JLabel[] displays) {
        List<Elevator> elevators = system.getElevators();
        for (int i = 0; i < elevators.size(); i++) {
            displays[i].setText("Elevator " + (i + 1) + " at floor: " + elevators.get(i).getCurrentFloor());
        }
    }

    public static void main(String[] args) {
        ElevatorSystem system = new ElevatorSystem(3); // 3 лифта
        SwingUtilities.invokeLater(() -> {
            ElevatorGUI gui = new ElevatorGUI(system);
            gui.setVisible(true);
        });
    }
}
