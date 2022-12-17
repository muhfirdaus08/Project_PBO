import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class MainMenu extends Composite {

    private JButton load_image_button;
    private JButton exit_button;
    private JLabel message;

    public MainMenu(String name, JFrame window, GuiHandler gui_handler, Resolution window_size) {
        super(name, window, gui_handler, window_size);
        this.load_image_button = null;
        this.exit_button = null;

        this.setLayout(new BorderLayout(5, 5));
        this.setBorder(BorderFactory.createLineBorder(new Color(151, 0, 222), 3));
    }

    //Loads GUI sections of this composite.
    public void loadSections() {
        this.loadTitleSection();
        this.loadCenterSection();
        // this.loadFooterSection();
    }

    //Loads "Puzzle Game" label and adds it to the composite.
    private void loadTitleSection() {
        JPanel title_section = new JPanel();
        title_section.setName("TITLE");
        title_section.setBorder(BorderFactory.createEmptyBorder(51, 0, 19, 0));
        title_section.setOpaque(false);

        JLabel title = new JLabel("Image Puzzle Game");
        Font title_font = new Font("Arial", Font.BOLD, 29);
        title.setForeground(new Color(151, 0, 222));
        title.setFont(title_font);

        title_section.add(title);

        this.add(title_section, BorderLayout.NORTH);
    }

    //Loads buttons, labels and adds them to the composite.
    private void loadCenterSection() {
        //Buttons
        JPanel center_section = new JPanel();
        center_section.setName("BUTTONS");
        center_section.setLayout(new BoxLayout(center_section, BoxLayout.PAGE_AXIS));
        center_section.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        center_section.setOpaque(false);
        

        this.load_image_button = new JButton("Load Image");
        this.exit_button = new JButton("Exit");
        this.load_image_button.setAlignmentX(CENTER_ALIGNMENT);
        this.exit_button.setAlignmentX(CENTER_ALIGNMENT);
        this.load_image_button.setMaximumSize(new Dimension(125, 40));
        this.exit_button.setMaximumSize(new Dimension(125, 40));
        
        this.load_image_button.setBackground(new Color(151, 0, 222));
        this.load_image_button.setForeground(Color.WHITE);

        this.exit_button.setBackground(new Color(151, 0, 222));
        this.exit_button.setForeground(Color.WHITE);
        this.load_image_button.addActionListener(this);
        this.exit_button.addActionListener(this);

        //Message
        this.message = new JLabel();
        this.message.setForeground(Color.black);
        this.message.setAlignmentX(CENTER_ALIGNMENT);

        center_section.add(this.load_image_button);
        center_section.add(Box.createRigidArea(new Dimension(0, 5)));
        center_section.add(this.exit_button);
        center_section.add(Box.createRigidArea(new Dimension(0, 30)));
        center_section.add(this.message);

        this.add(center_section, BorderLayout.CENTER);
    }

    //Lets the user to choose img file. After approval proceeds to the options menu.
    private File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this.window);

        //When image has loaded successfully.
        if(result == JFileChooser.APPROVE_OPTION) {
            Game.img = fileChooser.getSelectedFile();
            this.gui_handler.showComposite("OPTIONS");
        }
        else {
            this.message.setText("Image was not loaded...");
        }

        return null;
    }

    //Paints composite's background.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image background_img = null;
        try {
            background_img = ImageIO.read(new File("image/backgroundGame.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(background_img, 0, 120, null);
    }

    //Buttons event listener.
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == this.load_image_button) {
            this.chooseFile();
        }
        else if(source == this.exit_button) {
            this.window.dispose();
            System.exit(0);
        }
    }
}
