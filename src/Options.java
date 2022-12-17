import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Options extends Composite {

    // playerName
    // private JTextField name_text_field;
    // public String name;

    //NumberOfBlocks
    private Integer jumlah_kotak;
    private JTextField jumlah_kotak_field;

    //Scale
    private String scale;
    private JComboBox scale_combo_box;

    //Message
    private JLabel message;

    //Return button
    private JButton return_button;

    //Start button
    private JButton start_button;

    public Options(String name, JFrame window, GuiHandler gui_handler, Resolution window_size) {
        super(name, window, gui_handler, window_size);
        this.jumlah_kotak = null;
        this.scale = "maximum";
        this.message = null;
        this.return_button = new JButton("Return");
        this.return_button.setForeground(Color.WHITE);
        this.return_button.setBackground(new Color(151, 0, 222));
        this.start_button = new JButton("Start");
        this.start_button.setForeground(Color.WHITE);
        this.start_button.setBackground(new Color(151, 0, 222));

        this.setLayout(new BorderLayout(5, 5));
        this.setBorder(BorderFactory.createLineBorder(new Color(151, 0, 222), 3));
    }

    //Loads GUI sections of this composite.
    public void loadSections() {
        this.loadTitleSection();
        this.loadCenterSection();
        this.loadButtonsSection();
    }

    //Loads "Game Options" label and adds it to the composite.
    private void loadTitleSection() {
        JPanel title_section = new JPanel();
        title_section.setName("TITLE");
        title_section.setBorder(BorderFactory.createEmptyBorder(45, 0, 19, 0));

        JLabel title = new JLabel("Game Options");
        Font title_font = new Font("Arial", Font.BOLD, 29);
        title.setForeground(new Color(151, 0, 222));
        title.setFont(title_font);

        title_section.add(title);

        this.add(title_section, BorderLayout.NORTH);
    }

    //Loads labels, fields, combo-box and adds them to the composite.
    private void loadCenterSection() {
        //Options section
        JPanel center_section = new JPanel();
        center_section.setName("OPTIONS");
        center_section.setLayout(new BoxLayout(center_section, BoxLayout.PAGE_AXIS));
        center_section.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10));

        //Font for option labels
        Font options_font = new Font("Arial", Font.BOLD, 14);

        // name player
        // JLabel nameLabel = new JLabel("Name:");
        // nameLabel.setLabelFor(this.name_text_field);
        // nameLabel.setForeground(new Color(151, 0, 222));
        // nameLabel.setFont(options_font);

        // this.name_text_field = new JTextField();
        // this.name_text_field.setAlignmentX(LEFT_ALIGNMENT);
        // this.name_text_field.setMaximumSize(new Dimension(90, 25));

        //Number of blocks option
        JLabel jumlah_kotak_label = new JLabel("Jumlah Kotak:");
        jumlah_kotak_label.setLabelFor(this.jumlah_kotak_field);
        jumlah_kotak_label.setForeground(new Color(151, 0, 222));
        jumlah_kotak_label.setFont(options_font);

        this.jumlah_kotak_field = new JTextField();
        this.jumlah_kotak_field.setAlignmentX(LEFT_ALIGNMENT);
        this.jumlah_kotak_field.setMaximumSize(new Dimension(90, 25));
        this.jumlah_kotak_field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent doc_event) {
                numberOfBlocksChange(doc_event);
            }

            @Override
            public void removeUpdate(DocumentEvent doc_event) {
                numberOfBlocksChange(doc_event);
            }

            @Override
            public void changedUpdate(DocumentEvent doc_event) {
                numberOfBlocksChange(doc_event);
            }
        });

        //Scale option label
        JLabel scale_label = new JLabel("Skala:");
        scale_label.setLabelFor(this.scale_combo_box);
        scale_label.setForeground(new Color(151, 0, 222));
        scale_label.setFont(options_font);

        //Combo box
        String values[] = { "Maximum", "Small", "Medium", "Large" };
        this.scale_combo_box = new JComboBox(values);
        this.scale_combo_box.setAlignmentX(LEFT_ALIGNMENT);
        this.scale_combo_box.setMaximumSize(new Dimension(90, 25));
        this.scale_combo_box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent item_event) {
                if(item_event.getStateChange() == ItemEvent.SELECTED) {
                    scaleChange(item_event);
                }
            }
        });

        //Message
        this.message = new JLabel();
        this.message.setForeground(Color.red);
        this.message.setAlignmentX(LEFT_ALIGNMENT);

        //Adding components to the section.
        // center_section.add(nameLabel);
        // center_section.add(this.name_text_field);
        center_section.add(Box.createRigidArea(new Dimension(0, 5)));
        center_section.add(jumlah_kotak_label);
        center_section.add(Box.createRigidArea(new Dimension(0, 5)));
        center_section.add(this.jumlah_kotak_field);
        center_section.add(Box.createRigidArea(new Dimension(0, 10)));
        center_section.add(scale_label);
        center_section.add(Box.createRigidArea(new Dimension(0, 5)));
        center_section.add(this.scale_combo_box);
        center_section.add(Box.createRigidArea(new Dimension(0, 30)));
        center_section.add(this.message);

        // name = "daus";
        // name = name_text_field.getText();
        // System.out.print("Nama pemain : "+name);

        this.add(center_section, BorderLayout.CENTER);
    }

    //Loads bottom buttons and adds them to the composite.
    private void loadButtonsSection() {
        JPanel buttons_section = new JPanel();
        buttons_section.setName("BUTTONS");
        buttons_section.setLayout(new BoxLayout(buttons_section, BoxLayout.LINE_AXIS));
        buttons_section.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        this.return_button.addActionListener(this);
        this.start_button.addActionListener(this);

        buttons_section.add(this.return_button);
        buttons_section.add(Box.createHorizontalGlue());
        buttons_section.add(this.start_button);

        this.add(buttons_section, BorderLayout.SOUTH);
    }

    //Initializes a new game with options provided by the user and starts it.
    private void startGame() {
        if(this.jumlah_kotak == null) {
            this.message.setText("Jumlah kotak kosong");
        }
        else {
            BufferedImage adjusted_img = this.adjustFile(Game.img);
            StartedGame started_game = new StartedGame("STARTED_GAME", this.window, this.gui_handler, adjusted_img, this.jumlah_kotak);

            started_game.loadSections();

            gui_handler.addComposite(started_game, started_game.getName());
            this.gui_handler.showComposite("STARTED_GAME");
        }
    }

    //Adjusts the image loaded by the user.
    private BufferedImage adjustFile(File file) {
        //Used to store file as an image and process it.
        BufferedImage img = null;
        //Reads input file and stores it as an image.
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            //TODO
        }

        img = this.resizeToFitImgSection(img);
        img = this.userCriteriaScale(img, this.scale);
        img = this.cropImageIfGivesRemainder(img, this.jumlah_kotak);

        return img;
    }

    //Scales image to square and resizes it if it's bigger than tiles section's dimensions.
    private BufferedImage resizeToFitImgSection(BufferedImage img) {

        //Tiles section's resolution is set to the maximum of OS's windows area.
        Resolution tiles_section_size = gui_handler.getMaximumWindowBounds();
        tiles_section_size.setWidth(tiles_section_size.getWidth() - StartedGame.LEFT_BAR_WIDTH);

        //Checks if the image's dimensions are beyond tiles sections' resolution.
        int scale_width = (img.getWidth() > tiles_section_size.getWidth()) ? tiles_section_size.getWidth() : img.getWidth();
        int scale_height = (img.getHeight() > tiles_section_size.getHeight()) ? tiles_section_size.getHeight() : img.getHeight();
        int scale_length;

        if(scale_width < scale_height) {
            scale_length = scale_width;
        }
        else {
            scale_length = scale_height;
        }

        //Dimensions of image are corrected by taskbar's height to fit into OS's windows area.
        if(this.checkIfBiggerThanOsWindowsArea(scale_length, scale_length)) {
            scale_length -= gui_handler.getOsTaskBarHeight() * 2;
        }

        //Used to resize image.
        BufferedImage scaled_image = new BufferedImage(scale_length, scale_length, img.getType());
        Graphics2D g2d = scaled_image.createGraphics();
        g2d.drawImage(img, 0, 0, scale_length, scale_length, null);

        return scaled_image;
    }

    //Checks if resolution passed as parameters is bigger than the screen resolution without taskbar.
    private boolean checkIfBiggerThanOsWindowsArea(int width, int height) {
        Resolution windows_area = this.gui_handler.getMaximumWindowBounds();

        if(width >= windows_area.getWidth()) {
            return true;
        }
        if(height >= windows_area.getHeight()) {
            return true;
        }

        return false;
    }

    //Scales image along user criteria.
    private BufferedImage userCriteriaScale(BufferedImage img, String scale) {
        if(!scale.toLowerCase().equals("maximum")) {
            int scaled_length = img.getHeight();

            switch (scale.toLowerCase()) {
                case "large":
                    scaled_length = scaled_length * 85 / 100;
                    break;
                case "medium":
                    scaled_length = img.getHeight() * 75 / 100;
                    break;
                case "small":
                    scaled_length = img.getHeight() * 65 / 100;
                    break;
            }

            BufferedImage scaled_image = new BufferedImage(scaled_length, scaled_length, img.getType());
            Graphics2D g2d = scaled_image.createGraphics();
            g2d.drawImage(img, 0, 0, scaled_length, scaled_length, null);
            g2d.dispose();

            img = scaled_image;
        }
        return img;
    }

    //Crops the square image if it gives remainder when dividing it's size by number of blocks.
    private BufferedImage cropImageIfGivesRemainder(BufferedImage img, int jumlah_kotak) {
        if(img.getHeight() % jumlah_kotak != 0) {
            int size_of_block = img.getHeight() / jumlah_kotak;
            int cropping_size = size_of_block * jumlah_kotak;
            img = img.getSubimage(0, 0, cropping_size, cropping_size);
        }

        return img;
    }

    //Validation of amount of blocks option.
    private void numberOfBlocksChange(DocumentEvent doc_event) {
        this.message.setText("");
        this.jumlah_kotak = null;

        Document document = doc_event.getDocument();
        String text = null;
        try {
            text = document.getText(0, document.getLength());
            if(!text.isEmpty()) {
                Integer jumlah_kotak = Integer.parseInt(text);
                if(jumlah_kotak < 2) {
                    this.message.setText("Jumlah kotak minimal 2");
                }
                else if(jumlah_kotak > 30) {
                    this.message.setText("Jumlah kotak maksimal 31");
                }
                else {
                    this.jumlah_kotak = jumlah_kotak;
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            this.message.setText("Bukan sebuah angka");
        }
    }

    //Sets scale depending on the event.
    private void scaleChange(ItemEvent item_event) {
        this.scale = item_event.getItem().toString().toLowerCase();
    }

    //Handles events generated by buttons.
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == this.return_button) {
            this.gui_handler.showComposite("MAIN_MENU");
        }
        else if(source == this.start_button) {
            this.startGame();
        }
    }
}
