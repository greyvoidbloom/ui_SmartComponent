import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// MainFrame represents the main app window
public class MainFrame extends JFrame {
    private JPanel EntityPlayground; // Panel for entities
    private JLabel MainHeading; // Main title
    private JButton btnCreateEntity; // Button to create entities
    private List<Entity> entities; // List to hold entities

    // Constructor to set up components and list
    public MainFrame() {
        initComponents();
        entities = new ArrayList<>();
    }

    // Initialize GUI components
    private void initComponents() {
        MainHeading = new JLabel();
        EntityPlayground = new JPanel();
        btnCreateEntity = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MainHeading.setFont(new Font("Lower Pixel", Font.PLAIN, 13));
        MainHeading.setText("SMART COMPONENT UI TESTING");

        btnCreateEntity.setText("Create Entity");
        btnCreateEntity.addActionListener(evt -> btnCreateEntityActionPerformed(evt));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainHeading, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreateEntity)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(EntityPlayground, GroupLayout.PREFERRED_SIZE, 615, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(MainHeading)
                    .addComponent(btnCreateEntity))
                .addGap(18, 18, 18)
                .addComponent(EntityPlayground, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }

    // Handle button click to create entities
    private void btnCreateEntityActionPerformed(java.awt.event.ActionEvent evt) {
        Entity entity = new Entity();
        entities.add(entity);
        EntityPlayground.add(entity.getLabel());
        EntityPlayground.revalidate();
        EntityPlayground.repaint();
    }

    // Main method to launch the app
    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

// Entity represents draggable entities
class Entity {
    private JLabel label; // Label for the entity
    private int x, y; // Coordinates for dragging

    // Constructor to set up the entity
    public Entity() {
        ImageIcon logo = new ImageIcon("./logo.png");
        label = new JLabel(logo);
        label.setSize(label.getPreferredSize());
        label.setOpaque(true);

        label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseReleased(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        label.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - x;
                int dy = e.getY() - y;
                int newX = label.getX() + dx;
                int newY = label.getY() + dy;

                if (newX >= 0 && newX + label.getWidth() <= label.getParent().getWidth() &&
                    newY >= 0 && newY + label.getHeight() <= label.getParent().getHeight()) {
                    label.setLocation(newX, newY);
                }
            }
        });
    }

    // Get the label of the entity
    public JLabel getLabel() {
        return label;
    }
}
