//EcoPuzzle.java
//4/11/22
// EcoPuzzle is a biology game focused on teaching high schoolers cell bio. 
// They have two options for game modes that they can play. One is the 
// slide puzzle mode wherein they will first have to answer 8 questions about
// which cell part corresponds to a given function. They can then start
// moving pieces around on the grid and arrange them by the cell part's 
// size. They second game mode is the pycto game mode wherein the user
// chooses a cell part and has to piece together a puzzle of its picture. 
/// This is the main code file for running the program. 

import javax.swing.JPanel; 
import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Insets; 

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox; 

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JMenu; 
import javax.swing.JMenuBar; 
import javax.swing.JMenuItem; 

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.MouseListener; 
import java.awt.event.MouseEvent; 

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter; 

import java.awt.Image; 
import javax.imageio.ImageIO; 

import javax.swing.Timer; 


public class EcoPuzzle
 {
	 public static void main(String[] args)
	 {
	    JFrame frame = new JFrame("Eco-Puzzle");
		frame.setLayout(new BorderLayout());
		MainPanel panel = new MainPanel();
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(960, 700);
		frame.setLocation(200, 140);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 }
}

// This is the main panel which contains the giant card layout 
class MainPanel extends JPanel
{
	private PlayPanelHolder playpan; // this is an instance of pph that will be used 
	public MainPanel()
	{
		CardLayout maincards = new CardLayout(); 
		setLayout(maincards); 
		Color lightgreen = new Color(135, 200, 126);
		HomePanel hp = new HomePanel(maincards, this); 
		add(hp, "home");
		playpan = new PlayPanelHolder(maincards, this); 
		add(playpan, "navigate");  
		InstructionsPanel ip = new InstructionsPanel(maincards, this); 
		add(ip, "instructions"); 
		FlashCardPanel fcp = new FlashCardPanel(maincards, this);
		add(fcp, "flashcards"); 
	}
	
	// this exits the panel 
	public void close()
	{
		this.removeAll();  
		System.exit(1); 
	}
	
	// this resets the play panel
	public void reset()
	{
		playpan.resetAll(); 
	}
	
	
}

// This panel contains the game title and all the buttons to navigate to different panels within Main Panel's card layout. 
// It also contains the exit button. 
class HomePanel extends JPanel 
{
	private CardLayout cards; // this is the card layout used by the main panel which has the homepanel as a card 
	private MainPanel main;  // the main panel instance containing the cardlayout 
	private String pictName; // name of the background picture
	private Image picture; // holds the picture thats added to the background
	private int imagestartx; // this is the leftmost coordinate of the part of the gradient that needs to be shown
	private int increment; // this is how much the gradient moves every time the timer goes off
	private HomePanel home; // This stores an instance of this home panel
	
	// creates the title and all the buttons 
	public HomePanel(CardLayout cardsIn, MainPanel mainin)
	{
		main = mainin; 
		cards = cardsIn; 
		setLayout(null); 
	    JLabel label = new JLabel("Eco-Puzzle");
	    label.setFont(new Font("Sans Serif", Font.PLAIN, 75)); 
	    label.setBounds(280, 120, 450, 70); 
	    add(label);
	    imagestartx = 0; 
	    increment = 2; 
	    getMyImage(); 
	    home = this; 
	    
	    TimerListener tl = new TimerListener(); 
	    Timer timer = new Timer(100,tl);
	    timer.start();  
	    
	     
	    ButtonListener bl = new ButtonListener(); 
	    
	    JButton play = new JButton("Play");  
	    play.addActionListener(bl);
	    play.setBounds(380, 220, 200, 40); 
	    add(play);
	    
	    JButton flashcards = new JButton("Flash Cards");
	    flashcards.addActionListener(bl);
	    flashcards.setBounds(380, 280, 200, 40);
	    add(flashcards);
	    
	    JButton instruction = new JButton("How to Play"); 
	    instruction.addActionListener(bl); 
	    instruction.setBounds(380, 340, 200, 40); 
	    add(instruction);
	     
	     
	    JButton exit = new JButton("Exit"); 
	    exit.addActionListener(bl);
	    exit.setBounds(380, 400, 200, 40);
	    add(exit);
	    
	 }
	 
	 // draws the background image 
	 public void paintComponent(Graphics g)
	 {
	     super.paintComponent(g);
	     g.drawImage(picture, 0, 0, 960, 700, imagestartx, 0, imagestartx+140, 220, this); 
	 }
	 
	 // This opens the file with the Image inside of it 
	 public void getMyImage()
	 {
		pictName = "Gradient2.png"; 
		File pictFile = new File(pictName); 
		try
		{
			picture = ImageIO.read(pictFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + pictName + " can't be found.\n\n"); 
			e.printStackTrace(); 
		}
	 }
	 
	 
	 // Checks which button was pressed using its action command and then uses the card layout 
	 // to switch to that respective panel
	 class ButtonListener implements ActionListener
	 {
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand(); 
			if(command.equals("Play"))
			{
		        cards.show(main, "navigate");    
			}
			else if(command.equals("How to Play"))
			{
			    cards.show(main, "instructions"); 		 
			}
			else if(command.equals("Flash Cards"))
			{
		    	 cards.show(main, "flashcards"); 
			}
			else if(command.equals("Exit"))
			{
				System.exit(1);
			}
		}
	 }
	 
	 class TimerListener implements ActionListener
	 {
		 public void actionPerformed(ActionEvent evt)
		 {
			 imagestartx += increment; 
			 if(imagestartx == 140 || imagestartx == 0 )
				increment *= -1; 
			 home.repaint(); 
		 }
	 }
	
}

// This panel is reused multiple times in places across the game in order to navigate back to the home panel
//  as it is able to access the main panel card layout and has a back button 
class TopPanel extends JPanel{
	 private String title; // this is the text in the label that appears in the center of the top bar 
	 private CardLayout cards; // this is the card layout which contains all the major panels 
	 private MainPanel main; // this is the instance of main panel which has the card layout
	 private Color darkgreen; 
	 
	 public TopPanel(String labelIn, CardLayout cardsIn, MainPanel mainIn)
	 {
	     title = labelIn; 
	     cards = cardsIn; 
	     main = mainIn; 
	     darkgreen = new Color(21, 79, 48); 
	     setBackground(darkgreen); 
	     
	     Font font = new Font("Monospace", Font.PLAIN, 20); 
	     BorderLayout topborder = new BorderLayout(); 
	     setLayout(topborder);
	     JButton back = new JButton("Home"); 
	     ButtonListener bbl = new ButtonListener();
	     back.addActionListener(bbl);
	     back.setFont(font);
	     add(back, BorderLayout.WEST);
	     JButton exit = new JButton("Exit"); 
	     exit.setFont(font); 
	     add(exit, BorderLayout.EAST); 
	     JPanel titlePanel = new JPanel(); 
	     titlePanel.setBackground(darkgreen); 
	     titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
	     JLabel titleLabel = new JLabel(title);
	     titleLabel.setFont(font); 
	     titlePanel.add(titleLabel); 
	     add(titlePanel, BorderLayout.CENTER);
	 }
	
	 // this checks the action command and if the user clicked the back button it will use the card layout to show the
	 // home panel again
	 class ButtonListener implements ActionListener
{
	     public void actionPerformed(ActionEvent evt)
	     {
	         String command = evt.getActionCommand(); 
	         if(command.equals("Home"))
	         {
	             cards.show(main, "home"); 
	             main.reset(); 
	         }
	         else if(command.equals("Exit"))
	         {
				 main.close(); 
			 }
	     }
	 }
}

// this panel contains the flash cards for each cell part, it's size, and it's function
class FlashCardPanel extends JPanel
{
	private CardLayout cards; // this is the card layout which contains all the major panels
	private MainPanel main; // this is the instance of main panel which has the card layout 
	private String[] parts; // this an array of all the cell part names
	private String[] functions; // an array for all the cell part functions
	private String[] images; // and array for all the image file names
	private Image[] image; // an array of all the images
	private int questionNum = 0; // this is the flashcard number the user is on
	private Color creme; // the background color of the panel
	
	public FlashCardPanel(CardLayout cardsIn, MainPanel mainIn)
	{
		cards = cardsIn; 
		main = mainIn; 
		parts = new String[8]; 
		functions = new String[8];
		images = new String[8]; 
		image = new Image[8]; 
		creme = new Color(212, 202, 163); 
		readFunctionsFile("Functions.txt"); 
		readPartsFile("CellParts.txt"); 
		readImagesFile("Images.txt"); 
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		TopPanel playPanelTop = new TopPanel("Flashcards:", cards, main);
		add(playPanelTop, BorderLayout.NORTH); 
		FlashCard card = new FlashCard(); 
		//card.setInfo(parts[0], functions[0]); 
		card.setInfo(parts[0], functions[0], image[0]); 
		card.setBackground(creme); 
		ChangeFlashcardPanel left = new ChangeFlashcardPanel(card, "Back", questionNum, parts, functions, image); 
		left.setPreferredSize(new Dimension(200, 650)); 
		left.setBackground(creme); 
		ChangeFlashcardPanel right = new ChangeFlashcardPanel(card, "Next", questionNum, parts, functions, image); 
		right.setPreferredSize(new Dimension(200, 650)); 
		right.setBackground(creme); 
		add(left, BorderLayout.WEST); 
		add(right, BorderLayout.EAST); 
		card.setPreferredSize(new Dimension(300, 200));
		add(card, BorderLayout.CENTER); 
	}
	
	// This uses a while loop to read in the information in the
	// Functions.txt file. 
	public void readFunctionsFile(String fileName)
	{
		String name = fileName;
		Scanner scan =  null; 
		File file = new File(fileName);
		try
		{
			scan = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
		
		int i = 0; 
		while(scan.hasNext())
		{
			functions[i] = scan.nextLine(); 
			i++; 
		}
	}
	
	// This uses a while loop to read in the information in the
	// Parts.txt file. 
	public void readPartsFile(String fileName)
	{
		String name = fileName;
		Scanner scan =  null; 
		File file = new File(fileName);
		try
		{
			scan = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
		
		int i = 0; 
		while(scan.hasNext())
		{
			parts[i] = scan.nextLine(); 
			i++; 
		}
	}
	
	// This uses a while loop to read in the information in the
	// Images.txt file. 
	public void readImagesFile(String fileName)
	{
		String name = fileName;
		Scanner scan =  null; 
		File file = new File(fileName);
		try
		{
			scan = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
		
		int i = 0; 
		while(scan.hasNext())
		{
			images[i] = scan.nextLine(); 
			i++; 
		}
		
		for(int j = 0; j < i; j++)
		{
			String pictName = images[j]; 
			File pictFile = new File(pictName); 
			try
			{
				image[j] = ImageIO.read(pictFile);
			}
			catch(IOException e)
			{
				System.err.println("\n\n" + pictName + " can't be found.\n\n"); 
				e.printStackTrace(); 
			}
		}
	}

}

// these panels contain the buttons to switch between the flashcards
class ChangeFlashcardPanel extends JPanel implements ActionListener
{
	private int questionNum; // this stores the card number the user is currently on 
	private String[] functions; // this stores all the cell organelle functions
	private String[] parts; // this stores all the cell parts 
	private Image[] images; // this stores all the cell part images
	private FlashCard card; // instance of flashcard where the information is displayed
	
	public ChangeFlashcardPanel(FlashCard cardIn, String text, int numIn, String[] partsIn, String[] functionsIn, Image[] imagesin)
	{
		functions = functionsIn; 
		parts =  partsIn; 
		images = imagesin; 
		card = cardIn; 
		questionNum = numIn; 
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 0, 300); 
		setLayout(fl); 
		JButton button = new JButton(text); 
		button.addActionListener(this); 
		add(button); 
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		String command = evt.getActionCommand(); 
		
		if(command.equals("Next"))
		{
			questionNum++; 
		}
		else if(command.equals("Back"))
		{
			questionNum--; 
		}
		
		if(questionNum > 7)
		{
			questionNum = 0; 
		}
		else if(questionNum < 0)
		{
			questionNum = 7; 
		}
		
		card.setInfo(parts[questionNum], functions[questionNum], images[questionNum]); 
		
	}
	
}

class FlashCard extends JPanel  
{
	private String part; // This is the cell part being displayed on the flash card
	private String function; // The function that corresponds to the cell part
	private Image image; // An image of the cell part
	
	
	public FlashCard()
	{
		part = "";
		function = ""; 
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		Font partFont = new Font("Serif", Font.BOLD, 40); 
		g.setFont(partFont); 
		g.drawString(part, 50, 150); 
		Font infoFont = new Font("Serif", Font.PLAIN, 20); 
		g.setFont(infoFont); 
		g.drawString(function, 50, 300); 
		g.drawImage(image, 400, 125, 150, 150, null); 
	}
	
	// This changes the information shown on the flashcard
	public void setInfo(String partIn, String functionIn, Image imageIn) 
	{
		part = partIn;
		function = functionIn; 
		image = imageIn; 
		repaint();
	}

}


	
// This is the main instructions panel which contains a TopPanel and a PanelSwitcher panel
class InstructionsPanel extends JPanel 
{
	private CardLayout cards; // this is the card layout which contains all the major panels
	private MainPanel main; // this is the instance of main panel which has the card layout 
	public InstructionsPanel(CardLayout cardsIn, MainPanel mainIn)
	{
		cards = cardsIn; 
		main = mainIn; 
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		TopPanel playPanelTop = new TopPanel("Instructions:", cards, main);
		add(playPanelTop, BorderLayout.NORTH); 
		InstructionSwitcher instructionswitch = new InstructionSwitcher();
		add(instructionswitch, BorderLayout.CENTER); 
	}
}

// This panel contains the JMenu with an option to switch between the instructions of the pycto puzzle
// and slide puzzle
class InstructionSwitcher extends JPanel
{
	private CardLayout cards; // this is the card layout which contains all the major panels
	private JTextArea instructionsLabel; // This label contains the instructions for the game depending on what the user picks 
	private Color creme; 
	public InstructionSwitcher()
	{
		creme = new Color(212, 202, 163); 
		setBackground(creme); 
		BorderLayout bl = new BorderLayout(); 
		setLayout(bl);
		JMenuBar bar = new JMenuBar(); 
		JMenuItem slideinst = new JMenuItem("Slide"); 
		JMenuItem pyctoinst = new JMenuItem("Pycto"); 
		ItemListener il= new ItemListener(); 
		slideinst.addActionListener(il);
		pyctoinst.addActionListener(il); 
		bar.add(slideinst); 
		bar.add(pyctoinst); 
		bar.setMargin(new Insets(0, 10, 20, 10)); 
		add(bar, BorderLayout.NORTH);  
		instructionsLabel = new JTextArea("You will be given a grid of 9 boxes, with one square empty. Each one except for the empty one will contain "
				+ "a question about which cell part corresponds to the function mentioned "
				+ "you must answer all the questions correctly without making more than 3 mistakes in order to start arranging the slide puzzle. "
				+ "After you have finished answering, arrange the cell parts by their size, from smallest to biggest, top-down, left to right. ");
		instructionsLabel.setBackground(creme);
		instructionsLabel.setWrapStyleWord(true);
		instructionsLabel.setLineWrap(true);
		instructionsLabel.setEditable(false);
		instructionsLabel.setMargin(new Insets(10, 10, 20, 10));
		add(instructionsLabel, BorderLayout.CENTER);
	}
	
	// This checks which menu item the user has clicked and changes the instruction label based on their choice 
	class ItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand(); 
			if(command.equals("Slide"))
			{
				instructionsLabel.setText("You will be given a grid of 9 boxes, with one square empty. Each one except for the empty one will contain "
						+ "a question about which cell part corresponds to the function mentioned "
						+ "you must answer all the questions correctly without making more than 3 mistakes in order to start arranging the slide puzzle. "
						+ "After you have finished answering, arrange the cell parts by their size, from smallest to biggest, top-down, left to right. ");
			}
			else if(command.equals("Pycto"))
			{
				instructionsLabel.setText("Pick a cell part. You will be given a grid of puzzle pieces"
						+ " that all form the image of the cell part and you must swap the pieces until you have formed the correct picture.");
			}
			
		}
	} 
	 
}

// This is the Play Panel which contains all the cards for the Game Play section
class PlayPanelHolder extends JPanel
{
	private CardLayout cards2; // This is the card layout play panel uses to hold the Starting play panel and then the two game mode panels 
	private CardLayout cards; // this is the card layout which contains all the major panels
	MainPanel main; // this is the instance of main panel which has the card layout 
	private SlideGamePanel sgamepan; // this is the instance of the slide game panel which is a part of this panel's card layout
	private PyctoGamePanel pgamepan; // this is the instance of the pycto game panel which is a part of this panel's card layout
	private SlideResultsPanel sliderespan; // this is an instance of the results panel for the slide puzzle
	
	public PlayPanelHolder(CardLayout cardsIn, MainPanel mainIn)
	{
		cards = cardsIn; 
		main = mainIn; 
		
		cards2 = new CardLayout(); 
		setLayout(cards2);
		PlayPanel playpan = new PlayPanel(cards, main, cards2, this); 
		sliderespan = new SlideResultsPanel(cards, mainIn, this);
		sgamepan = new SlideGamePanel(cards, mainIn, cards2, this, sliderespan);  
		SlideWinPanel swinpan = new SlideWinPanel(cards, mainIn, this); 
		pgamepan = new PyctoGamePanel(cards, mainIn, cards2, this); 
		add(playpan, "1"); 
		add(sgamepan, "2"); 
		add(sliderespan, "4"); 
		add(pgamepan, "3"); 
		add(swinpan, "5"); 
	}
	
	
	public void resetAll()
	{ 
		cards2.show(this, "1"); 
		this.removeAll(); 
		PlayPanel playpan = new PlayPanel(cards, main, cards2, this); 
		sliderespan = new SlideResultsPanel(cards, main, this);
		sgamepan = new SlideGamePanel(cards, main, cards2, this, sliderespan);  
		SlideWinPanel swinpan = new SlideWinPanel(cards, main, this); 
		swinpan.setBackground(Color.BLUE); 
		pgamepan = new PyctoGamePanel(cards, main, cards2, this); 
		add(playpan, "1"); 
		add(sgamepan, "2"); 
		add(sliderespan, "4"); 
		add(pgamepan, "3"); 
		add(swinpan, "5");  
	}
}

// This Panel has a top panel and contains a picker panel where the user can pick which game they want to play
class PlayPanel extends JPanel 
{
	private CardLayout cards; // this is the card layout which contains all the major panels
	private MainPanel main; // this is the instance of main panel which has the card layout 
	
	public PlayPanel(CardLayout cardsIn, MainPanel mainIn, CardLayout cards2In, PlayPanelHolder playpanholder)
	{
	   cards = cardsIn; 
	   main = mainIn; 
	   BorderLayout bl = new BorderLayout();
	   setLayout(bl);
	   TopPanel playPanelTop = new TopPanel("Pick a Game Mode Here:", cards, main);
	   add(playPanelTop, BorderLayout.NORTH); 
	   CardLayout pickGameMode = new CardLayout(); // this is for Game picker panel and is declared here so that the other classes can also use this 
	   GamePickerPanel pickerpan = new GamePickerPanel(pickGameMode, cards2In, playpanholder); 
	   add(pickerpan, BorderLayout.CENTER); 
	   pickGameRadioPanel radioPicker = new pickGameRadioPanel(pickerpan, pickGameMode); 
	   add(radioPicker, BorderLayout.SOUTH);
	}
}

// This panel contains the two game info panels as cards 
class GamePickerPanel extends JPanel 
{
	private CardLayout cards; // this is the card layout which game picker uses to switch between the slide puzzle info and the pycto puzzle info
	public GamePickerPanel(CardLayout cardsIn, CardLayout cardsIn2, PlayPanelHolder playpanhold)
	{
		cards = cardsIn; 
		setLayout(cards); 
		SlideInfoPanel sip = new SlideInfoPanel(cardsIn2, playpanhold); 
		PyctoInfoPanel pip = new PyctoInfoPanel(cardsIn2, playpanhold); 
		add(sip, "slideinfo"); 
		add(pip, "pyctoinfo");
	}
}

// This is the panel which has the instructions and the play button for the slide puzzle
class SlideInfoPanel extends JPanel
{
	private CardLayout cards; // this is play panel holder's card layout
	private PlayPanelHolder panel; // this is an instance of play panel holder to switch between panels if the user 
	// says they want to play the slide puzzle 
	public SlideInfoPanel(CardLayout cardsIn, PlayPanelHolder panelIn)
	{
		cards = cardsIn; 
		panel = panelIn; 
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 440, 50);
		setLayout(fl); 
		JTextArea instructions = new JTextArea("You will be given a grid of 16 boxes, with one square empty. Each one except for the empty one will contain "
				+ "a question about which cell part corresponds to the function mentioned "
				+ "you must answer all the questions correctly without making more than 3 mistakes in order to start arranging the slide puzzle. "
				+ "After you have finished answering, arrange the cell parts by their size, from smallest to biggest, top-down, left to right. ");
		instructions.setPreferredSize(new Dimension(480, 200)); 
		instructions.setBackground(Color.WHITE);
		instructions.setWrapStyleWord(true);
		instructions.setLineWrap(true);
		instructions.setEditable(false);
		add(instructions);
		JButton playslide = new JButton("Play Slide Mode!");
		SlideButtonHandler sbl = new SlideButtonHandler();
		playslide.addActionListener(sbl);
		add(playslide);
	}
	
	// this checks if the user hit the play button and then uses the card layout to show the slide puzzle game panel 
	class SlideButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Play Slide Mode!"))
			{
				cards.show(panel, "2");
			}
		}
	}
}

//This is the panel which has the instructions and the play button for the pycto puzzle
class PyctoInfoPanel extends JPanel
{
	private CardLayout cards; // this is play panel holder's card layout
	private PlayPanelHolder panel; // this is an instance of play panel holder to switch between panels if the user 
	// says they want to play the pycto puzzle 
	public PyctoInfoPanel(CardLayout cardsIn, PlayPanelHolder panelIn)
	{
		cards = cardsIn; 
		panel = panelIn; 
		
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 440, 50);
		setLayout(fl); 
		JTextArea instructions = new JTextArea("Pick a cell part. You will be given a grid of puzzle pieces "
				+ "that all form the image of the cell part and you must swap the pieces until you have formed the correct picture.");
		instructions.setPreferredSize(new Dimension(480, 200)); 
		instructions.setBackground(Color.WHITE);
		instructions.setWrapStyleWord(true);
		instructions.setLineWrap(true);
		instructions.setEditable(false);
		add(instructions);
		JButton playpycto = new JButton("Play Pycto Mode!");
		PyctoButtonHandler pbl = new PyctoButtonHandler();
		playpycto.addActionListener(pbl);
		add(playpycto); 
	}	
	
	// this checks if the user hit the play button and then uses the card layout to show the pycto puzzle game panel 
	class PyctoButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Play Pycto Mode!"))
			{
				cards.show(panel, "3");
			}
		}
	}
}

// this contains the toppanel, the slide puzzle holder, and a submit button
class SlideGamePanel extends JPanel 
{
	private CardLayout cards; // This is the card layout with all the major panels
	private MainPanel main; // this is the instance of main panel with the card layout and that is used to change cards 
	private CardLayout cards2; // This is the card layout for the playpanelholder
	private PlayPanelHolder playpan; // this is the play panel holder that contains the card layout 
	private TileHolder holder; // holds an instance of the tile holder
	private int mistakes; // this is the number of mistakes the user has made
	
	public SlideGamePanel(CardLayout cardsIn, MainPanel mainIn, CardLayout cards2in, PlayPanelHolder playpanin, SlideResultsPanel respan)
	{
		cards = cardsIn; 
		main = mainIn;
		cards2 = cards2in; 
		playpan = playpanin;  
		FlowLayout bl = new FlowLayout(FlowLayout.CENTER, 500, 50); 
		setLayout(bl);
		TopPanel tp = new TopPanel("Game Panel", cards, main); 
		add(tp); 
		holder = new TileHolder(cards2, playpan, respan); 
		holder.setPreferredSize(new Dimension(450, 450)); 
		add(holder);
		JButton submit = new JButton("Submit"); 
		SubmitHandler sl = new SubmitHandler(); 
		submit.addActionListener(sl);
		add(submit);
	}
	
	
	// if the user clicks submit and has sent in the right solution
	// it takes them back to the home page
	class SubmitHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt) {
			if(holder.checkSolved())
			{
				cards2.show(playpan, "5"); 
			}
		}
	}
	
	
}

// This is the panel that shows up when the user wins the slide puzzle
class SlideWinPanel extends JPanel
{
	private CardLayout cards;  // This is the instance of a CardLayout for the main page
	private MainPanel main; // This is the instance of MainPanel that has a card layout
	private PlayPanelHolder pph; // This is the instance of PlayPanelHolder that can be used to reset the panels
	
	public SlideWinPanel(CardLayout cardsIn, MainPanel mainIn, PlayPanelHolder pphin)
	{
		cards = cardsIn; 
		main = mainIn; 
		pph = pphin; 
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 300, 200));
		JLabel label = new JLabel("YOU WON!"); 
		Font font = new Font("Serif", Font.BOLD, 50); 
		label.setFont(font); 
		add(label); 
		
		ButtonHandler rh = new ButtonHandler(); 
		JButton homebutton = new JButton("Return to home"); 
		homebutton.addActionListener(rh);
		add(homebutton); 
	}
	
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand(); 
			if(cmd.equals("Return to home"))
			{
				cards.show(main, "home"); 
				pph.resetAll(); 
			}
		}
	}
}

class SlideResultsPanel extends JPanel implements ActionListener 
{
	private CardLayout cards; // this is the cardlayout which has all the major panels
	private MainPanel main; // this is the instance of main panel which has the card layout
	private String[] mistakes; // this is an array of all the mistakes made
	private int mistakenum; // this is the number of mistakes made 
	private PlayPanelHolder playpanholder; // this is an instance of the play panel holder that contains this card
	private JTextArea mistakesarea; // this is the text are where users can see the mistakes they made in the game
	private JLabel label; // this label contains the amount of mistakes the user made
	
	public SlideResultsPanel(CardLayout cardsIn, MainPanel mainIn, PlayPanelHolder playpanholderin)
	{
		FlowLayout bl = new FlowLayout(FlowLayout.CENTER, 500, 50); 
		setLayout(bl);
		mistakenum = 0; 
		cards = cardsIn; 
		main = mainIn; 
		playpanholder = playpanholderin; 
		mistakes = new String[100]; 
		String textareamistakes = ""; 
		
		for(int i = 0; i < mistakenum; i++)
		{
			if(mistakes[i].length()>1)
			{
				textareamistakes += mistakes[i] + "\n"; 
			}
		}
		label = new JLabel("You made " + mistakenum + " mistakes. Check out the Flashcards section for more information.");
		add(label); 
		mistakesarea = new JTextArea(textareamistakes); 
		mistakesarea.setPreferredSize(new Dimension(450, 300)); 
		mistakesarea.setWrapStyleWord(true);
		mistakesarea.setLineWrap(true);
		mistakesarea.setEditable(false);
		add(mistakesarea); 
		JButton home = new JButton("Home"); 
		home.addActionListener(this); 
		add(home); 
	}
	
	// This opens the mistakes.txt file and reads in the mistakes 
	// the user made. It adds it to an array. 
	public void openFile()
	{
		String fileName = "Mistakes.txt";
		Scanner scan = null; 
		File file = new File(fileName);
		try
		{
			scan = new Scanner(file);
		}
		
		catch(FileNotFoundException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
		
		int i = 0; 
		
		while(scan.hasNext())
		{ 
			//String line = scan.nextLine(); 
			mistakes[i] = scan.nextLine();
			i++; 
		}
		
		mistakenum = i-1; 
		label.setText("You made " + mistakenum + " mistakes."); 
		String textaream = ""; 
		for(int j = 1; j < i; j++)
		{
			textaream += j + ". " + mistakes[j] + "\n"; 
		}
		mistakesarea.setText(textaream); 
		repaint(); 
		
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		String cmd = evt.getActionCommand(); 
		if(cmd.equals("Home"))
		{
			cards.show(main, "home"); 
			playpanholder.resetAll(); 
		}
	}
	
}


// this is the grid which has all the slide puzzle pieces in it
class TileHolder extends JPanel implements KeyListener, MouseListener
{
	private String[][] parts = {{"1", "2", "3"},
				{"4", "5", "6"},
				{"7", "8", ""}}; // has the name of the cell parts 
	private String[][] functions = {{"1f", "2f", "3f"},
			{"4f", "5f", "6f"},
			{"7f", "8f", ""}}; // has the functions of each of the cell parts 
	private SlideTile[][] scramble = new SlideTile[3][3]; // will contain the tiles in their scrambled order 
	private int emptcol; // contains the column of the empty square
	private int emptrow; // contains the row of the empty square
	private SlideTile[][] tiles = new SlideTile[3][3]; // contains the array of slide tiles in the correct order
	private GridLayout gl;  // the grid layout used for this panel
	private boolean move; // whether or not the pieces can be moved 
	private int mistakes; // this keeps a tally of the number of mistakes the user has made
	private int moves; // this is the number of times the user moved a piece
	private PrintWriter writer; // This is the object used to write to the users mistakes file
	private CardLayout cards; // this is the card layout of the playpanelholder
	private PlayPanelHolder playpan; // this is an instance of play panel holder
	private SlideResultsPanel resultspan; // this is an instance of the results panel 
	private final int length = 3; // this is the length and width of the grid
	
	public TileHolder(CardLayout cardsIn, PlayPanelHolder playpanIn, SlideResultsPanel sliderespanIn)
	{
		cards = cardsIn; 
		playpan = playpanIn; 
		resultspan = sliderespanIn; 
		gl = new GridLayout(3, 3); 
		setLayout(gl); 
		
		move = false; 
		
		readPartsFile("CellParts.txt"); 
		readFunctionsFile("Functions.txt"); 
		wipeMistakes(); 
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < length; j++)
			{
				if(parts[i][j].equals(""))
				{
					tiles[i][j] = new SlideTile(Color.GRAY, "",functions[i][j], i, j, this, writer);
					emptrow = i; 
					emptcol = j;  
				}
				else
					tiles[i][j] = new SlideTile(Color.GREEN, parts[i][j], functions[i][j], i, j, this, writer); 
				  
			}
		}
		scramble(); 
		for(int i = 0; i < length; i++)
		{ 
			for(int j = 0; j < length; j++)
			{
				add(scramble[i][j]);
				scramble[i][j].setPreferredSize(new Dimension(150, 150));
			}
		}
		addKeyListener(this); 
		addMouseListener(this);
	}
	
	
	// This method uses a double for loop in order to randomly generate 
	// spots for the slide puzzle pieces 
	public void scramble()
	{
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < length; j++)
			{	
				scramble[i][j] = tiles[i][j]; 
			}
		}
		
		for(int i = 0; i < 300; i++)
		{
			int move = (int)(Math.random() * 4 + 1); 
			if(move == 1 && emptrow > 0)
			{
				SlideTile temp = scramble[emptrow-1][emptcol];
				scramble[emptrow-1][emptcol] = scramble[emptrow][emptcol]; 
				scramble[emptrow][emptcol] = temp; 
				emptrow--; 
			}
			else if(move == 2 && emptrow < 2)
			{
				SlideTile temp = scramble[emptrow+1][emptcol];
				scramble[emptrow+1][emptcol] = scramble[emptrow][emptcol]; 
				scramble[emptrow][emptcol] = temp; 
				emptrow++; 	
			}
			else if(move == 3 && emptcol < 2 )
			{
				SlideTile temp = scramble[emptrow][emptcol+1];
				scramble[emptrow][emptcol+1] = scramble[emptrow][emptcol]; 
				scramble[emptrow][emptcol] = temp; 
				emptcol++; 
			}
			else if(move == 4 && emptcol > 0)
			{
				SlideTile temp = scramble[emptrow][emptcol-1];
				scramble[emptrow][emptcol-1] = scramble[emptrow][emptcol]; 
				scramble[emptrow][emptcol] = temp; 
				emptcol--; 
			}
		}
	}
	
	
	// This method loops through the place where the user placed each tile 
	// and where the tile is actually supposed to be. If none of the tiles 
	// are out of place it returns true. Otherwise, it returns false and
	// uses SlideTiles setter method to change the background color of the 
	// wrong tiles to red. 
	public boolean checkSolved()
	{
		boolean solved = true; 
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < length; j++)
			{
				String userpart = scramble[i][j].getName(); 
				if(!userpart.equals(parts[i][j]))
				{
					solved = false;
					if(!userpart.equals(""))
					{
						scramble[i][j].setColor(Color.RED); 
						addMistake("Mistakes.txt", "You put " + userpart + " in the wrong location"); 
					}
				}
				else if(!userpart.equals(""))
				{
					scramble[i][j].setColor(Color.GREEN); 
				}
			}
				
		}
		if(!solved)
			incrementMistakes(); 
		System.out.println(solved); 
		return solved; 
	}
	
	// This wipes the mistake file to ensure there are not any left 
	// from the last game. 
	public void wipeMistakes()
	{
		String fileName = new String("Mistakes.txt"); 
		File file = new File(fileName);
		PrintWriter writer = null;  
		try
		{
			writer = new PrintWriter(file); 
		}
		catch(IOException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
		
		writer.println(""); 
		writer.close(); 
	}
	
	// This keeps track of the amount of mistakes the user has made 
	// and closes the printwriter once they have made too many mistakes
	public void incrementMistakes()
	{
		mistakes++; 
		if(mistakes > 3)
		{
			resultspan.openFile(); 
			cards.show(playpan, "4");
		}
	}
	
	// This uses a double for loop to read in all the cell parts
	// from cellparts.txt and add them to the parts array
	public void readPartsFile(String fileName)
	{
		String name = fileName;
		Scanner scan; 
		File file = new File(fileName);
		try
		{
			scan = new Scanner(file);
			for(int i = 0; i < length; i++)
			{
				for(int j = 0; j < length; j++)
				{
					if(j!=2|| i!= 2)
					{
						parts[i][j] = scan.nextLine(); 
					}
				}
			}
		}
		catch(FileNotFoundException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
	
		
	}
	
	// this file opens the mistakes file and creates a print writer
	// that can write to that file. 
	public void addMistake(String fileName, String mistake)
	{
		String name = fileName; 
		writer = null; 
		File outfile = new File(fileName); 
		try
		{
			writer = new PrintWriter(new FileWriter(outfile, true)); 
		}
		catch(IOException e)
		{
			e.printStackTrace(); 
			System.exit(1);
		}
		writer.println(mistake);
		writer.close(); 
	}
	
	
	// This uses a double for loop to read in the information in the
	// Functions.txt file. 
	public void readFunctionsFile(String fileName)
	{
		Scanner scan =  null; 
		File file = new File(fileName);
		try
		{
			scan = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < length; j++)
			{
				if(j!=2|| i!= 2)
				{
					functions[i][j] = scan.nextLine(); 
				}
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		requestFocusInWindow(); 
	}
	
	//checks that all the text fields have been filled so that the user cannot move before they have completed them 
	public boolean checkMoveable()
	{
		boolean moves = true; 
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < length; j++)
			{
				if(scramble[i][j].getMoveable() == false)
				{
					moves = false; 
				} 
			}
		}		
		return moves; 
	}
	
	// this checks which key the user pressed. If move is true (the user answered all the cell function questions), it will 
	// allow the user to move the tiles based on the key pressed. The user is not actually moving the tiles, but the information
	// in the tiles is switched to make it look like they are. 
	public void keyPressed(KeyEvent evt)
	{
		int code = 0; 
		code = evt.getKeyCode(); 
		
		move = checkMoveable(); 
	
		if(move)
		{
			if(code == KeyEvent.VK_DOWN && emptrow > 0)
			{
				Color OColor = scramble[emptrow][emptcol].getColor(); 
				Color SwapColor = scramble[emptrow-1][emptcol].getColor(); 
				String PartToSwap = scramble[emptrow-1][emptcol].getName(); 
				
				scramble[emptrow][emptcol].setColor(SwapColor); 
				scramble[emptrow-1][emptcol].setColor(OColor); 
				scramble[emptrow][emptcol].setName(PartToSwap); 
				scramble[emptrow-1][emptcol].setName(""); 
				emptrow--; 
				moves++;
			}
			else if(code == KeyEvent.VK_UP && emptrow < 2)
			{
				Color OColor = scramble[emptrow][emptcol].getColor(); 
				Color SwapColor = scramble[emptrow+1][emptcol].getColor(); 
				String PartToSwap = scramble[emptrow+1][emptcol].getName(); 
				
				scramble[emptrow][emptcol].setColor(SwapColor); 
				scramble[emptrow+1][emptcol].setColor(OColor); 
				scramble[emptrow][emptcol].setName(PartToSwap); 
				scramble[emptrow+1][emptcol].setName(""); 
				emptrow++; 	
				moves++;
			}
			else if(code == KeyEvent.VK_LEFT && emptcol < 2)
			{
				Color OColor = scramble[emptrow][emptcol].getColor(); 
				Color SwapColor = scramble[emptrow][emptcol+1].getColor(); 
				String PartToSwap = scramble[emptrow][emptcol+1].getName(); 
				
				scramble[emptrow][emptcol].setColor(SwapColor); 
				scramble[emptrow][emptcol+1].setColor(OColor); 
				scramble[emptrow][emptcol].setName(PartToSwap); 
				scramble[emptrow][emptcol+1].setName(""); 
				emptcol++;
				moves++; 
			}
			
			else if(code == KeyEvent.VK_RIGHT && emptcol > 0)
			{	
				Color OColor = scramble[emptrow][emptcol].getColor(); 
				Color SwapColor = scramble[emptrow][emptcol-1].getColor(); 
				String PartToSwap = scramble[emptrow][emptcol-1].getName(); 
				
				scramble[emptrow][emptcol].setColor(SwapColor); 
				scramble[emptrow][emptcol-1].setColor(OColor); 
				scramble[emptrow][emptcol].setName(PartToSwap); 
				scramble[emptrow][emptcol-1].setName(""); 
				emptcol--; 
				moves++;
			}
			
		}
		repaint(); 
	}
	
	public void keyTyped(KeyEvent evt){}
	public void keyReleased(KeyEvent evt){}
	
	// This method checks that move is true. Then based on where the user clicked, it find which row and column they clicked in. Next, it 
	// switched the information between the tiles to make it look like they switched places. 
	public void mousePressed(MouseEvent evt)
	{
		int x; 
		int y; 
		int rowclicked; 
		int colclicked; 
		x = evt.getX(); 
		y = evt.getY(); 
		
		rowclicked = (int)y/150; 
		colclicked = (int)x/150;
		
		move = checkMoveable(); 
		
		if(move)
		{
			if(rowclicked < 2 && rowclicked + 1 == emptrow && colclicked == emptcol) {
				Color OColor = scramble[emptrow][emptcol].getColor(); 
				Color SwapColor = scramble[rowclicked][colclicked].getColor(); 
				String PartToSwap = scramble[rowclicked][colclicked].getName(); 
				
				scramble[emptrow][emptcol].setColor(SwapColor); 
				scramble[rowclicked][colclicked].setColor(OColor); 
				scramble[emptrow][emptcol].setName(PartToSwap); 
				scramble[rowclicked][colclicked].setName(""); 
				emptrow--; 
			}
			else if(rowclicked > 0  && rowclicked - 1 == emptrow && colclicked == emptcol)
			{
				Color OColor = scramble[emptrow][emptcol].getColor(); 
				Color SwapColor = scramble[rowclicked][colclicked].getColor(); 
				String PartToSwap = scramble[rowclicked][colclicked].getName(); 
				
				scramble[emptrow][emptcol].setColor(SwapColor); 
				scramble[rowclicked][colclicked].setColor(OColor); 
				scramble[emptrow][emptcol].setName(PartToSwap); 
				scramble[rowclicked][colclicked].setName(""); 
				emptrow++; 	
			}
			else if(colclicked > 0 && rowclicked == emptrow && colclicked - 1 == emptcol)
			{
				Color OColor = scramble[emptrow][emptcol].getColor(); 
				Color SwapColor = scramble[rowclicked][colclicked].getColor(); 
				String PartToSwap = scramble[rowclicked][colclicked].getName(); 
				
				scramble[emptrow][emptcol].setColor(SwapColor); 
				scramble[rowclicked][colclicked].setColor(OColor); 
				scramble[emptrow][emptcol].setName(PartToSwap); 
				scramble[rowclicked][colclicked].setName(""); 
				emptcol++; 
			}
			else if(colclicked < 2 && rowclicked == emptrow && colclicked == emptcol - 1)
			{
				Color OColor = scramble[emptrow][emptcol].getColor(); 
				Color SwapColor = scramble[rowclicked][colclicked].getColor(); 
				String PartToSwap = scramble[rowclicked][colclicked].getName(); 
				
				scramble[emptrow][emptcol].setColor(SwapColor); 
				scramble[rowclicked][colclicked].setColor(OColor); 
				scramble[emptrow][emptcol].setName(PartToSwap); 
				scramble[rowclicked][colclicked].setName(""); 
				emptcol--; 
			}
		}
		repaint(); 
	}
	public void mouseClicked(MouseEvent evt){}
	public void mouseReleased(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}

}

// This panel contains all the information for a slide tile. It has a card layout to switch between the function 
// and the cell part. 
class SlideTile extends JPanel 
{
	private String cellPart; // the cell part to be displayed 
	private int col; // the column it is located in
	private int row; // the row it is located in
	private Color backg; // the background color 
	private boolean moveable; // whether or not the user has entered something into the text field 
	private String function; // what the function of the cell part is 
	private FunctionTile ft; // The function panel that is added to the card layout first
	private LabelTile lt; // The Label panel that is added to the card layout second
	private TileHolder holder; // This is an instance of the Tile holder that holds this slide tile
	private CardLayout tilecards; // this is the instance of a card layout being used by this class. 
	
	public SlideTile(Color bg, String cellPartIn, String function, int colIn, int rowIn, TileHolder holderIn, PrintWriter writerIn)
	{
		holder = holderIn; 
		backg = bg; 
		setBackground(backg); 
		moveable = false; 
		cellPart = cellPartIn; 
		col = colIn; 
		row = rowIn; 
		tilecards = new CardLayout(); 
		setLayout(tilecards);
		ft = new FunctionTile(function, tilecards, this);
		ft.setBackground(bg);
		add(ft, "function");
		lt = new LabelTile(); 
		lt.setBackground(bg);
		add(lt, "label");
		if(function.equals(""))
		{
			tilecards.show(this, "label"); 
			moveable = true; 
		}
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
	}
	// is able to be called from outside the class to force it to repaint
	public void redraw()
	{
		repaint(); 
	}
	
	// allows the tile holder class to access the cell part name 
	public String getName()
	{
		return cellPart; 
	}
	
	// allows the tile holder class to access the cell part name 

	public Color getColor()
	{
		return backg;  
	}
	
	// allows the tile holder class to access the cell part name 

	public boolean getMoveable()
	{
		return moveable; 
	}
	
	// allows the tile holder class to change the cell part name 
	public void setName(String nameIn)
	{
		cellPart = nameIn; 
		lt.setText(cellPart); 
	}
	
	// this designates the tile as an empty tile meaning it does not 
	// show the question text field 
	public void setEmpty()
	{
		moveable = true; 
		tilecards.show(this, "label"); 
	}
	// allows the tile holder class to change the cell part name 
	public void setColor(Color color)
	{
		backg = color; 
		lt.setBackground(color); 
	}
	
	// this shows the cell function and the text field and switches cards to the cell part panel once the user enters something in the 
	// field 
	class FunctionTile extends JPanel implements ActionListener
	{
		JTextField field; // the text field for the user to enter the cell part  
		CardLayout cards; // the card layout used by the slide tile 
		SlideTile tile; // this instance of slide tile with the card layout 
		String function; // this is the function of the cell part
		public FunctionTile(String functionin, CardLayout cardsIn, SlideTile tileIn)
		{
			cards = cardsIn; 
			tile = tileIn; 
			function = functionin; 
			setLayout(new FlowLayout(FlowLayout.CENTER, 75, 15));
			JTextArea label = new JTextArea(function); 
			label.setEditable(false); 
			label.setLineWrap(true); 
			label.setWrapStyleWord(true); 
			label.setBackground(backg); 
			label.setPreferredSize(new Dimension(110, 80)); 
			add(label);
			field = new JTextField(10); 
			field.addActionListener(this);
			add(field);
		}
		
		public void actionPerformed(ActionEvent evt)
		{
			String ans = field.getText(); 
			if(ans.trim().equalsIgnoreCase(cellPart.trim()))
			{
				cards.show(tile, "label"); 
				moveable = true; 
			}
			else
			{
				String mistakemade = "The " + cellPart + " " + function + " not the " + ans + ".";
				holder.addMistake("Mistakes.txt", mistakemade);  
				holder.incrementMistakes(); 
			}
		}
	}
	
	// this shows the cell part on the tile   
	class LabelTile extends JPanel
	{
		JLabel part; // this label contains the name of the cell part 
		public LabelTile()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 60, 60)); 
			part = new JLabel(cellPart); 
			add(part);
		}
		
		// this allows us to switch locations of tiles by moving their information 
		public void setText(String StringIn)
		{
			part.setText(StringIn); 
			repaint();
		}
	}
	
	
}

// this runs the pycto game
class PyctoGamePanel extends JPanel 
{
	private PyctoCardHolder holder; 
	public PyctoGamePanel(CardLayout cardsIn, MainPanel mainIn, CardLayout cards2in, PlayPanelHolder playholderin)
	{
		BorderLayout bl = new BorderLayout(); 
		setLayout(bl);
		TopPanel tp = new TopPanel("Game Panel", cardsIn, mainIn); 
		add(tp, BorderLayout.NORTH); 
		holder = new PyctoCardHolder(cardsIn, mainIn, cards2in, playholderin); 
		add(holder, BorderLayout.CENTER); 
	}
}

class PyctoCardHolder extends JPanel
{
	private MainPanel main; // this is an instance of the main panel which holds all the major cards
	private CardLayout mainpanelcards; // this is the card layout that mainpanel uses
	private PlayPanelHolder playholder; // this is the panel with the card layout for all the gameplay related panels
	private CardLayout playpanelcards; // this is the cardlayout that holds all the gameplay panels
	
	public PyctoCardHolder(CardLayout cardsIn, MainPanel mainIn, CardLayout cards2in, PlayPanelHolder playholderin)
	{
		CardLayout cards = new CardLayout(); 
		setLayout(cards); 
		mainpanelcards = cardsIn; 
		main = mainIn; 
		playholder = playholderin; 
		playpanelcards = cards2in; 
		PyctoDisplay display = new PyctoDisplay(cards, this); 
		PickPyctoImage pick = new PickPyctoImage(cards, this, display);
		PyctoWinPanel winpan = new PyctoWinPanel(mainpanelcards, main, cards2in, playholderin, cards, this);  
		add(pick, "pickpart"); 
		add(display, "display"); 
		add(winpan, "win"); 
	}
}

class PyctoWinPanel extends JPanel
{
	private MainPanel main; // this is an instance of the main panel which holds all the major cards
	private CardLayout mainpanelcards; // this is the card layout that mainpanel uses
	private PlayPanelHolder playholder; // this is the panel with the card layout for all the gameplay related panels
	private CardLayout playpanelcards; // this is the cardlayout that holds all the gameplay panels
	private PyctoCardHolder pch; // this it the instance of pycto card holder that has all the cards to run the pycto game
	private CardLayout pyctocards; // this is the cardlayout that holds all the pycto panels
	
	public PyctoWinPanel(CardLayout cards, MainPanel mainIn, CardLayout cards2in, PlayPanelHolder playholderin, CardLayout cards3in, PyctoCardHolder pchin )
	{
		mainpanelcards = cards; 
		main = mainIn; 
		playholder = playholderin; 
		playpanelcards = cards2in; 
		pch = pchin; 
		pyctocards = cards3in; 
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 300, 200));
		JLabel label = new JLabel("YOU WON!"); 
		Font font = new Font("Serif", Font.BOLD, 50); 
		label.setFont(font); 
		add(label); 
		
		ButtonHandler rh = new ButtonHandler(); 
		JButton homebutton = new JButton("Return to home"); 
		homebutton.addActionListener(rh);
		add(homebutton); 
	}
	
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand(); 
			if(cmd.equals("Return to home"))
			{
				playpanelcards.show(playholder, "1"); 
				pyctocards.show(pch, "pickpart"); 
				mainpanelcards.show(main, "home"); 
			}
		}
	}
}

class PickPyctoImage extends JPanel implements ActionListener
{
	private CardLayout cards; // This is the card layout for PyctoCardHolder
	private PyctoCardHolder pch; // has a card layout which allows this panel to move to the display and then the win panel
	private PyctoDisplay display; // This is an instance of the display panel which is next in the card layout
	private String[] images; // this is an array of image names
	private String[] names; // this is an array of the name of cell parts 
	private String imagename; // this is the image the user has chosen to play
	private JMenu menu; // This is the Jmenu which allows the user to pick which organelle to try to piece together
	private JMenuItem[] items; // array of jmenuitems which each contain the name of a cell organelle
	private final int num = 8; // constant for the number of parts there are
	private int clicked; // This is the index of the name, image name, and image of the cell part the user chose
	
	public PickPyctoImage(CardLayout cardsin, PyctoCardHolder pchin, PyctoDisplay displayin)
	{
		cards = cardsin; 
		pch = pchin; 
		display = displayin; 
		items = new JMenuItem[num]; 
		images = new String[num]; 
		names = new String[num];
		clicked = 0;  
		imagename = "ribosome.jpg"; 
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 500, 100)); 
		JLabel label = new JLabel("Pick your part"); 
		add(label); 
		JMenuBar bar = new JMenuBar(); 
		menu = new JMenu("Ribosome"); 
		readNames("CellParts.txt"); 
		readImages("Images.txt"); 
		for(int i = 0; i < num; i++)
		{
			items[i] = new JMenuItem(names[i]); 
			menu.add(items[i]); 
			items[i].addActionListener(this); 
		}
		bar.add(menu); 
		add(bar); 
		JButton next = new JButton("Play"); 
		ButtonHandler bh = new ButtonHandler(); 
		next.addActionListener(bh); 
		add(next); 
	}
	
	// this reads in the names from the names file and uses a while 
	// loop to read the names into an array until there are no names
	// left in the file
	public void readNames(String fileName)
	{
		String name = fileName;
		Scanner scan =  null; 
		File file = new File(fileName);
		try
		{
			scan = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
		
		int i = 0; 
		while(scan.hasNext())
		{
			names[i] = scan.nextLine(); 
			i++; 
		}
	}
	
	// this reads the image files and adds them to the images array
	// until there are none left to read
	public void readImages(String fileName)
	{
		String name = fileName;
		Scanner scan =  null; 
		File file = new File(fileName);
		try
		{
			scan = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.err.println(fileName + " could not be found");
			System.exit(1);
		}
		
		int i = 0; 
		while(scan.hasNext())
		{
			images[i] = scan.nextLine(); 
			i++; 
		}
		
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		String command = evt.getActionCommand(); 
		for(int i = 0; i < num; i++)
		{
			if(command.equals(names[i]))
			{
				clicked = i; 
				menu.setText(names[i]); 
			}
		}
	}
	
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand(); 
			System.out.print("command"); 
			if(command.equals("Play"))
			{
				imagename = images[clicked]; 
				display.setImage(imagename); 
				cards.show(pch, "display"); 
			}
		}
	}
}

class PyctoDisplay extends JPanel
{
	private PyctoTileHolder holder; // this is the panel with the grid of pycto tiles
	private CardLayout cards; // This is the cardlayout in pyctocardholder
	private PyctoCardHolder cardholder; // This is the panel which contains the card layout to switch to the win panel

	public PyctoDisplay(CardLayout cardsin, PyctoCardHolder holderin)
	{
		cards = cardsin; 
		cardholder = holderin; 
		
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 500, 50); 
		setLayout(fl);
		holder = new PyctoTileHolder(); 
		add(holder); 
		JButton submit = new JButton("Submit"); 
		ButtonHandler bh = new ButtonHandler(); 
		submit.addActionListener(bh); 
		add(submit); 
		
	}
	// takes in the image and uses it to set the holder image
	public void setImage(String image)
	{
		holder.setTileImage(image); 
	}
	
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if(holder.checkSolved())
			{
				cards.show(cardholder, "win"); 
			}
		}
	}
}

class PyctoTileHolder extends JPanel implements MouseListener
{
	private Image picture; //  this is the picture that the pycto is based on
	private String pictName; // this is the file name of the picture
	private PyctoTile[][] tiles = new PyctoTile[3][3]; // this array stores the actual position of the tiles
	private PyctoTile[][] scramble = new PyctoTile[3][3]; // this stores the tiles after the initial scramble and 
	// tracks where the user moves the pieces
	private int firstclickcol; // this holds the column of the first tile the user clicked
	private int firstclickrow; // this holds the row of the first tile the user clicked
	private int xincrement; // this holds the needed width of each tile in relation to the original image
	private int yincrement;  // this holds the needed height of each tile in relation to the original image
	private final int size = 3; // this is the length/width of the grid 
	
	public PyctoTileHolder()
	{
		GridLayout pyctoGrid = new GridLayout(3, 3); 
		setLayout(pyctoGrid); 
		getMyImage("Gradient2.png");  
		firstclickcol = -1; 
		firstclickrow = -1; 
		xincrement = 70; 
		yincrement = 70; 
		
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{ 
				
				tiles[i][j] = new PyctoTile(i * xincrement, j * yincrement, (i * xincrement) + xincrement, (j * yincrement) + yincrement, i, j, picture); 
				tiles[i][j].setPreferredSize(new Dimension(150, 150));
				scramble[i][j] = tiles[i][j];  
			}
		}
		
		scrambleBoard(); 
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				add(scramble[i][j]); 
			}
		}
		addMouseListener(this); 
	}
	
	// this method checks if all the tiles are in the right place 
	// using a double for loop 
	public boolean checkSolved()
	{
		boolean solved = true; 
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				int positions[] = scramble[i][j].getNeededPosition(); 
				int row = positions[0]; 
				int col = positions[1]; 
				if(row != i || col != j)
				{
					solved = false; 
					scramble[i][j].setColor(Color.RED); 
				}
				else
				{
					scramble[i][j].setColor(Color.BLACK);
				}
				scramble[i][j].repaint();  
			}
		}
		System.out.println(solved); 
		return solved; 
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		requestFocusInWindow(); 
	}
	
	// this sets the image of the pycto puzzle and assigns values
	// for the top left and bottom right of each tile
	public void setTileImage(String image)
	{
		
		getMyImage(image);
		xincrement = picture.getWidth(null)/3; 
		yincrement = picture.getHeight(null)/3; 
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				scramble[i][j].setImage(picture); 
				tiles[i][j].setPortions(i * yincrement, j * xincrement, (i * yincrement) + yincrement, (j * xincrement) + xincrement); 
				int[] info = scramble[i][j].getNeededPosition(); 
				int col = info[0]; 
				int row = info[1]; 
				scramble[i][j].setPortions(col * yincrement, row * xincrement, (col * yincrement) + yincrement, (row * xincrement) + xincrement); 
			}
		}
		
	}
	
	// this randomly switched two pieces on the board 300 times in order
	// to ensure it is scrambled well
	public void scrambleBoard()
	{
		for(int i = 0; i < 300; i++)
		{
			int[] places = new int[4]; 
			
			for(int j = 0; j < 4; j++)
			{
				places[j] = (int)(Math.random()*3); 
			}
			
			if(places[0] == places[2] && places[1] == places[3])
			{
				int index = (int)(Math.random()*4); 
				int former = places[index]; 
				while(places[index] == former)
				{
					places[index] = (int)(Math.random()*3); 
				}
				
				PyctoTile temp = scramble[places[0]][places[2]]; 
				scramble[places[0]][places[2]] = scramble[places[1]][places[3]]; 
				scramble[places[1]][places[3]] = temp; 
			}
		}
	}
	
	// this method reads a file to try and obtain the image
	public void getMyImage(String imagename)
	{
		pictName = imagename; 
		File pictFile = new File(pictName); 
		try
		{
			picture = ImageIO.read(pictFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + pictName + " can't be found.\n\n"); 
			e.printStackTrace(); 
		}
	}
	
	// this method uses the users x and y from the click in order to 
	// determine which row and column they clicked. It also checks 
	// whether it is the users first click or second click in order to 
	// figure out whether out to switch pieces. If it is the users second
	// click, it will use getter setters to switch the information of the 
	// two pieces. 
	public void mousePressed(MouseEvent evt)
	{
		if(firstclickcol == -1 && firstclickrow == -1)
		{
			firstclickrow = (int)(evt.getY()/150); 
			firstclickcol = (int)(evt.getX()/150); 
		}
		else
		{
			int secondclickrow = (int)(evt.getY()/150); 
			int secondclickcol = (int)(evt.getX()/150); 
			
			int[] firstinfo = scramble[firstclickrow][firstclickcol].getInfo(); 
			int[] secondinfo = scramble[secondclickrow][secondclickcol].getInfo(); 
			
			scramble[firstclickrow][firstclickcol].setInfo(secondinfo); 
			scramble[firstclickrow][firstclickcol].repaint(); 
			scramble[secondclickrow][secondclickcol].setInfo(firstinfo); 
			scramble[secondclickrow][secondclickcol].repaint(); 
			
			
			System.out.println(firstclickrow); 
			System.out.println(firstclickcol);
			System.out.println(secondclickrow); 
			System.out.println(secondclickcol);  
			
			firstclickrow = -1; 
			firstclickcol = -1; 
			
		}
	}
	public void mouseClicked(MouseEvent evt){}
	public void mouseReleased(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

class PyctoTile extends JPanel
{
	private int top; // top y coordinate of the portion of the original image that needs to be shown
	private int left;  // left most x coordinate of the portion of the original image that needs to be shown
	private int bottom; // bottom y coordinate of the portion of the original image that needs to be shown
	private int right; // right most x coordinate of the portion of the original image that needs to be shown
	private int col; // this is the column that the piece is supposed to be in
	private int row; // this is the row the piece is supposed to be in s
	private Image image; // This is the image that the the pycto puzzle uses
	private Color color; // this is the color of the border of the puzzle piece
	
	public PyctoTile(int topin, int leftin, int bottomin, int rightin, int rowin, int colin, Image imagein)
	{ 
		top = topin; 
		left = leftin; 
		bottom = bottomin; 
		right = rightin; 
		col = colin; 
		row = rowin; 
		image = imagein; 
		
		color = Color.BLACK; 
	}
	
	// This method returns all the coordinate infromation as well as 
	// the needed row and column
	public int[] getInfo()
	{
		int[] returnnums = new int[6]; 
		returnnums[0] = top; 
		returnnums[1] = left; 
		returnnums[2] = bottom; 
		returnnums[3] = right; 
		returnnums[4] = col; 
		returnnums[5] = row; 
		
		return returnnums; 
	}
	
	// this method returns the needed position of the pycto puzzle pieces
	public int[] getNeededPosition()
	{
		int[] positions = new int[2]; 
		positions[0] = row; 
		positions[1] = col; 
		return positions; 
	}
	
	// this sets the color that indicates to the user whether they put 
	// the piece in the right spot or not 
	public void setColor(Color colorin)
	{
		color = colorin;
	}
	
	// this sets the coordinates needed for the image and the row and 
	// column needed
	public void setInfo(int[] array)
	{
		top = array[0]; 
		left = array[1]; 
		bottom = array[2]; 
		right = array[3]; 
		col= array[4]; 
		row = array[5]; 
	}
	
	// this sets the image of the puzzle
	public void setImage(Image imagein)
	{
		image = imagein; 
	}
	
	// this sets the coordinates for the portion of the image that needs
	// to be shown
	public void setPortions(int topin, int leftin, int bottomin, int rightin)
	{
		top = topin; 
		left = leftin; 
		bottom = bottomin; 
		right = rightin; 
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		g.drawImage(image, 0, 0, 150, 150, left, top, right, bottom, this); 
		g.setColor(color); 
		g.drawRect(0, 0, 149, 149); 
	}
}

// this panel has two radio buttons in the center so the user can switch between information panels of both game modes. 
class pickGameRadioPanel extends JPanel 
{
	 GamePickerPanel panel; // This is an instance of game picker panel which has the card layout for the information panels
	 CardLayout cards; // this is the card layout that contains both the panels where the user can choose which game they want to play
	 public pickGameRadioPanel(GamePickerPanel pickpanIn, CardLayout cardsIn) 
	 {
		 panel = pickpanIn; 
		 cards = cardsIn; 
	     FlowLayout center = new FlowLayout(FlowLayout.CENTER); 
	     setLayout(center); 
	     ButtonGroup group = new ButtonGroup(); 
	     JRadioButton slide = new JRadioButton(""); 
	     JRadioButton pycto = new JRadioButton(""); 
	     SlideRadioListener sl = new SlideRadioListener(); 
	     PyctoRadioListener pl = new PyctoRadioListener(); 
	     slide.addActionListener(sl);
	     pycto.addActionListener(pl);
	     group.add(slide);
	     group.add(pycto); 
	     slide.addActionListener(sl);
	     pycto.addActionListener(pl);
	     slide.setSelected(true);
	     add(slide);
	     add(pycto);
	 }
	 
	 // This checks if the radio button for the slide puzzle has been clicked and displays its info if it has
	 class SlideRadioListener implements ActionListener
	 {
	     public void actionPerformed(ActionEvent evt)
	     {
	         String command = evt.getActionCommand(); 
	         if(command.equals(""))
	         {
	        	 cards.show(panel, "slideinfo");
	         }
	     }
	 }
	 
	 // This checks if the pycto button for the slide puzzle has been clicked and displays its info if it has
	 class PyctoRadioListener implements ActionListener
	 {
	     public void actionPerformed(ActionEvent evt)
	     {
	         String command = evt.getActionCommand(); 
	         if(command.equals(""))
	         {
	        	 cards.show(panel, "pyctoinfo");
	         }
	     }
	 }
}
