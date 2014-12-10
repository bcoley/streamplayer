package com.brett.processing.test;

import processing.core.PApplet;
import processing.core.PConstants;

public class CoverGenerator {

//    String api = "http://api.flickr.com/services/rest/?method=flickr.photos.search";
//    String flickrKey = "";
//    String photoUrl = "";
    //private PImage img;
    private int screenWidth = 800;
    private int screenHeight = 400;
    //private PFont titleFont;
    //private PFont authorFont;
    private boolean refresh = true;
    //private boolean autosave = false;
    private int minTitle = 2;
    private int maxTitle = 60;
    private int coverWidth = 200;
    private int coverHeight = 250;
    private int currentBook = 0;
    private int margin = 5;
    private int titleHeight = 55;
    private int authorHeight = 25;
    private int artworkStartX = 400;
    private int artworkStartY = 75;
    //private int fontSize = 14;
    private int coverBaseColor;
    private int coverShapeColor;
    private int baseColor = coverBaseColor;
    private int shapeColor = coverShapeColor;
    //private int baseVariation = 100;
    private int baseSaturation = 90;
    private int baseBrightness = 80;
    private int gridCount = 7;
    private int shapeThickness = 5;
    private String c64Letters = " qQwWeErRtTyYuUiIoOpPaAsSdDfFgGhHjJkKlL:zZxXcCvVbBnNmM1234567890.";
    private String title = "";
    private String author = "";
    //private String filename = "";
    //private String[] bookList;
    private PApplet parent = null;

    private String[][] books = {
            {
                "Bob", "Adventures of the Subgenius"
            }
            ,
            {
                "Laozi", "道德經"
            }
            , 
            {
                "Rachinskii, Sergei Aleksandrovich", "1001 задача для умственного счета"
            }
            , 
            {
                "Luo, Guanzhong", "粉妝樓1-10回"
            }
            , 
            {
                "Nobre, António Pereira", "Só"
            }
            , 
            {
                "Various", "Väinölä"
            }
            , 
            {
                "Zhang, Chao", "幽夢影 — Part 1"
            }
            , 
            {
                "Sunzi, active 6th century B.C.", "兵法 (Bīng Fǎ)"
            }
            , 
            {
                "Leino, Kasimir", "Elämästä"
            }
            , 
            {
                "Han, Ying, active 150 B.C.", "韓詩外傳, Complete"
            }
            , 
            {
                "Besant, Walter", "As we are and as we may be"
            }
            , 
            {
                "New York Trio", "Trio No. 1 in B Flat, Pt. 1"
            }
            , 
            {
                "Hale, Edward Everett, Sr.", "How to do it"
            }
            , 
            {
                "Milne, A. A. (Alan Alexander)", "If I may"
            }
            , 
            {
                "Lehtonen, Joel", "Kuolleet omenapuut Runollista proosaa"
            }
            , 
            {
                "Hassell, Antti Fredrik", "Jaakko Cook'in matkat Tyynellä merellä"
            }
            , 
            {
                "Hough, Emerson", "The Mississippi bubble"
            }
            , 
            {
                "Smith, George Adam", "Four psalms, XXIII, XXXVI, LII, CXXI; interpreted for practical use."
            }
            , 
            {
                "Canth, Minna", "Hanna"
            }
            , 
            {
                "Malot, Hector", "Baccara"
            }
            , 
            {
                "Bensusan, S. L. (Samuel Levy)", "Morocco"
            }
            , 
            {
                "Gauguin, Paul", "Noa Noa"
            }
            , 
            {
                "Rinehart, Mary Roberts", "K"
            }
            , 
            {
                "Various", "Blackwood's Edinburgh Magazine — Volume 53, No. 327, January, 1843"
            }
            , 
            {
                "Livingstone, David", "The Last Journals of David Livingstone, in Central Africa, from 1865 to His Death, Volume II (of 2), 1869-1873"
            }
            , 
            {
                "Ames, Azel", "The Mayflower and Her Log; July 15, 1620-May 6, 1621 — Complete"
            }
            , 
            {
                "Jane Austen", "Pride and Prejudice"
            }
            , 
            {
                "Arthur Conan Doyle", "The Adventures of Sherlock Holmes"
            }
            , 
            {
                "Franz Kafka", "Metamorphosis"
            }
            , 
            {
                "Miguel de Cervantes Saavedra", "Don Quixote"
            }
            , 
            {
                "Oscar Wilde", "The Importance of Being Earnest: A Trivial Comedy for Serious People"
            }
            , 
            {
                "Frederick Douglass", "Narrative of the Life of Frederick Douglass, an American Slave"
            }
            , 
            {
                "E.M. Berens", "Myths and Legends of Ancient Greece and Rome"
            }
            , 
            {
                "Ambrose Bierce", "The Devil's Dictionary"
            }
            , 
            {
                "Edgar Rice Burroughs", "A Princess of Mars"
            }
            , 
            {
                "Ludwig Wittgenstein", "Tractatus Logico-Philosophicus"
            }
            , 
            {
                "Mark Twain", "Life on the Mississippi"
            }
            , 
            {
                "John Cleland", "Memoirs of Fanny Hill"
            }
            , 
            {
                "Joshua Rose", "Mechanical Drawing Self-Taught"
            }
            , 
            {
                "P.G. Wodwhouse", "Right Ho, Jeeves"
            }
            , 
            {
                "Andre Norton", "All Cats Are Grey"
            }
            , 
            {
                "Lange, Algot", "In the Amazon jungle"
            }
            , 
            {
                "Michelson, Miriam", "In the bishop's carriage"
            }
            , 
            {
                "James, Henry", "In the cage"
            }
            , 
            {
                "Burroughs, John", "In the Catskills"
            }
            , 
            {
                "Doyle, Arthur Conan", "The cabman's story"
            }
            , 
            {
                "Yonge, Charlotte M.", "The caged lion"
            }
            , 
            {
                "Wright, Harold Bell", "The calling of Dan Matthews"
            }
            , 
            {
                "Grey, Zane", "The call of the canyon"
            }
            , 
            {
                "Williams and Williams", "A History of Science — Volume 1"
            }
            , 
            {
                "Williams and Williams", "A History of Science — Volume 2"
            }
            , 
            {
                "Williams and Williams", "A History of Science — Volume 3"
            }
            , 
            {
                "Williams and Williams", "A History of Science — Volume 4"
            }
    };
    public void setup() {
        coverBaseColor = parent.color(204, 153, 0);
        coverShapeColor = parent.color(50);
        parent.size(screenWidth, screenHeight); //, "processing.core.PGraphicsRetina2D");
        parent.background(0);
        parent.noStroke();
        parent.frameRate(12);
        // titleFont = loadFont("AvenirNext-Bold-" + fontSize + ".vlw");
        // authorFont = loadFont("AvenirNext-Regular-" + fontSize + ".vlw");
        //String config[] = loadStrings("config.txt");
        //flickrKey = "772eeb11e2b2e7332dbbcd4bcfbb32ac"; //config[0];
    }
    public void draw() {
        if (refresh) {
            refresh = false;
            getCurrentBook();
            processColors();
            drawBackground();
            drawArtwork();
            drawText();
        }
    }
    void getCurrentBook() {
        String[] book = books[currentBook];
        //JSONObject book = JSONObject.parse(bookList[currentBook]);
        title = book[1];
        author = book[0];
        //filename = author + title + ".png";
    }
    void drawBackground() {
        parent.background(50);
        parent.fill(255);
        parent.rect(artworkStartX, 0, coverWidth, coverHeight);
    }
    void drawText() {
        //…
        parent.fill(50);
        //textFont(titleFont, fontSize);
        parent.textLeading(14);
        parent.text(title, artworkStartX+margin, margin+margin, coverWidth - (2 * margin), titleHeight);
        // fill(255);
        //textFont(authorFont, fontSize);
        parent.text(author, artworkStartX+margin, titleHeight, coverWidth - (2 * margin), authorHeight);
    }
    String c64Convert() {
        // returns a string with all the c64-letter available in the title or a random set if none
        String result = "";
        int i, len = title.length();
        char letter;
        for (i=0; i<len; i++) {
            letter = title.charAt(i);
            // println("letter:" + letter + " num:" + int(letter));
            if (c64Letters.indexOf(letter) == -1) {
                int anIndex = ((letter)) % c64Letters.length();//floor(random(c64Letters.length()));
                letter = c64Letters.charAt(anIndex);
            }
            // println("letter:" + letter);
            result = result + letter;
        }
        // println("result:" + result);
        return result;
    }
    void drawArtwork() {
        breakGrid();
        int i, j, gridSize=coverWidth/gridCount;
        int item = 0;
        parent.fill(baseColor);
        parent.rect(artworkStartX, 0, coverWidth, margin);
        parent.rect(artworkStartX, artworkStartY, coverWidth, coverWidth);
        String c64Title = c64Convert();
        // println("c64Title.length(): "+c64Title.length());
        for (i=0; i<gridCount; i++) {
            for (j=0; j<gridCount; j++) {
                char character = c64Title.charAt(item%c64Title.length());
                drawShape (character, artworkStartX+(j*gridSize), artworkStartY+(i*gridSize), gridSize);
                item++;
            }
        }
    }
    
    
    void breakGrid() {
        int len = title.length();
        // println("title length:"+len);
        if (len < minTitle) len = minTitle;
        if (len > maxTitle) len = maxTitle;
        gridCount = (int)(PApplet.map(len, minTitle, maxTitle, 2, 11));
    }
    
    
    void processColors() {
        int counts = title.length() + author.length();
        int colorSeed = (int)(PApplet.map(counts, 2, 80, 30, 260));
        parent.colorMode(PConstants.HSB, 360, 100, 100);
        // int rndSeed = colorSeed + int(random(baseVariation));
        // int darkOnLight = (floor(random(2))==0) ? 1 : -1;
        shapeColor = parent.color(colorSeed, baseSaturation, baseBrightness-(counts%20));// 55+(darkOnLight*25));
        baseColor = parent.color((colorSeed+180)%360, baseSaturation, baseBrightness);// 55-(darkOnLight*25));
        // println("inverted:"+(counts%10));
        // if length of title+author is multiple of 10 make it inverted
        if (counts%10==0) {
            int tmpColor = baseColor;
            baseColor = shapeColor;
            shapeColor = tmpColor;
        }
        PApplet.println("baseColor:"+baseColor);
        PApplet.println("shapeColor:"+shapeColor);
        parent.colorMode(PConstants.RGB, 255);
    }
    
    void drawShape(char k, int x, int y, int s) {
        parent.ellipseMode(PConstants.CORNER);
        parent.fill(shapeColor);
        switch (k) {
        case 'q':
        case 'Q':
            parent.ellipse(x, y, s, s);
            break;
        case 'w':
        case 'W':
            parent.ellipse(x, y, s, s);
            s = s-(shapeThickness*2);
            parent.fill(baseColor);
            parent.ellipse(x+shapeThickness, y+shapeThickness, s, s);
            break;
        case 'e':
        case 'E':
            parent.rect(x, y+shapeThickness, s, shapeThickness);
            break;
        case 'r':
        case 'R':
            parent.rect(x, y+s-(shapeThickness*2), s, shapeThickness);
            break;
        case 't':
        case 'T':
            parent.rect(x+shapeThickness, y, shapeThickness, s);
            break;
        case 'y':
        case 'Y':
            parent.rect(x+s-(shapeThickness*2), y, shapeThickness, s);
            break;
        case 'u':
        case 'U':
            parent.arc(x, y, s*2, s*2, PConstants.PI, PConstants.PI+PConstants.HALF_PI);
            parent.fill(baseColor);
            parent.arc(x+shapeThickness, y+shapeThickness, (s-shapeThickness)*2, (s-shapeThickness)*2, PConstants.PI, PConstants.PI+PConstants.HALF_PI);
            break;
        case 'i':
        case 'I':
            parent.arc(x-s, y, s*2, s*2, PConstants.PI+PConstants.HALF_PI, PConstants.TWO_PI);
            parent.fill(baseColor);
            parent.arc(x-s+shapeThickness, y+shapeThickness, (s-shapeThickness)*2, (s-shapeThickness)*2, PConstants.PI+PConstants.HALF_PI, PConstants.TWO_PI);
            break;
        case 'o':
        case 'O':
            parent.rect(x, y, s, shapeThickness);
            parent.rect(x, y, shapeThickness, s);
            break;
        case 'p':
        case 'P':
            parent.rect(x, y, s, shapeThickness);
            parent.rect(x+s-shapeThickness, y, shapeThickness, s);
            break;
        case 'a':
        case 'A':
            parent.triangle(x, y+s, x+(s/2), y, x+s, y+s);
            break;
        case 's':
        case 'S':
            parent.triangle(x, y, x+(s/2), y+s, x+s, y);
            break;
        case 'd':
        case 'D':
            parent.rect(x, y+(shapeThickness*2), s, shapeThickness);
            break;
        case 'f':
        case 'F':
            parent.rect(x, y+s-(shapeThickness*3), s, shapeThickness);
            break;
        case 'g':
        case 'G':
            parent.rect(x+(shapeThickness*2), y, shapeThickness, s);
            break;
        case 'h':
        case 'H':
            parent.rect(x+s-(shapeThickness*3), y, shapeThickness, s);
            break;
        case 'j':
        case 'J':
            parent.arc(x, y-s, s*2, s*2, PConstants.HALF_PI, PConstants.PI);
            parent.fill(baseColor);
            parent.arc(x+shapeThickness, y-s+shapeThickness, (s-shapeThickness)*2, (s-shapeThickness)*2, PConstants.HALF_PI, PConstants.PI);
            break;
        case 'k':
        case 'K':
            parent.arc(x-s, y-s, s*2, s*2, 0, PConstants.HALF_PI);
            parent.fill(baseColor);
            parent.arc(x-s+shapeThickness, y-s+shapeThickness, (s-shapeThickness)*2, (s-shapeThickness)*2, 0, PConstants.HALF_PI);
            break;
        case 'l':
        case 'L':
            parent.rect(x, y, shapeThickness, s);
            parent.rect(x, y+s-shapeThickness, s, shapeThickness);
            break;
        case ':':
            parent.rect(x+s-shapeThickness, y, shapeThickness, s);
            parent.rect(x, y+s-shapeThickness, s, shapeThickness);
            break;
        case 'z':
        case 'Z':
            parent.triangle(x, y+(s/2), x+(s/2), y, x+s, y+(s/2));
            parent.triangle(x, y+(s/2), x+(s/2), y+s, x+s, y+(s/2));
            break;
        case 'x':
        case 'X':
            parent.ellipseMode(PConstants.CENTER);
            parent.ellipse(x+(s/2), y+(s/3), shapeThickness*2, shapeThickness*2);
            parent.ellipse(x+(s/3), y+s-(s/3), shapeThickness*2, shapeThickness*2);
            parent.ellipse(x+s-(s/3), y+s-(s/3), shapeThickness*2, shapeThickness*2);
            parent.ellipseMode(PConstants.CORNER);
            break;
        case 'c':
        case 'C':
            parent.rect(x, y+(shapeThickness*3), s, shapeThickness);
            break;
        case 'v':
        case 'V':
            parent.rect(x, y, s, s);
            parent.fill(baseColor);
            parent.triangle(x+shapeThickness, y, x+(s/2), y+(s/2)-shapeThickness, x+s-shapeThickness, y);
            parent.triangle(x, y+shapeThickness, x+(s/2)-shapeThickness, y+(s/2), x, y+s-shapeThickness);
            parent.triangle(x+shapeThickness, y+s, x+(s/2), y+(s/2)+shapeThickness, x+s-shapeThickness, y+s);
            parent.triangle(x+s, y+shapeThickness, x+s, y+s-shapeThickness, x+(s/2)+shapeThickness, y+(s/2));
            break;
        case 'b':
        case 'B':
            parent.rect(x+(shapeThickness*3), y, shapeThickness, s);
            break;
        case 'n':
        case 'N':
            parent.rect(x, y, s, s);
            parent.fill(baseColor);
            parent.triangle(x, y, x+s-shapeThickness, y, x, y+s-shapeThickness);
            parent.triangle(x+shapeThickness, y+s, x+s, y+s, x+s, y+shapeThickness);
            break;
        case 'm':
        case 'M':
            parent.rect(x, y, s, s);
            parent.fill(baseColor);
            parent.triangle(x+shapeThickness, y, x+s, y, x+s, y+s-shapeThickness);
            parent.triangle(x, y+shapeThickness, x, y+s, x+s-shapeThickness, y+s);
            break;
        case '7':
            parent.rect(x, y, s, shapeThickness*2);
            break;
        case '8':
            parent.rect(x, y, s, shapeThickness*3);
            break;
        case '9':
            parent.rect(x, y, shapeThickness, s);
            parent.rect(x, y+s-(shapeThickness*3), s, shapeThickness*3);
            break;
        case '4':
            parent.rect(x, y, shapeThickness*2, s);
            break;
        case '5':
            parent.rect(x, y, shapeThickness*3, s);
            break;
        case '6':
            parent.rect(x+s-(shapeThickness*3), y, shapeThickness*3, s);
            break;
        case '1':
            parent.rect(x, y+(s/2)-(shapeThickness/2), s, shapeThickness);
            parent.rect(x+(s/2)-(shapeThickness/2), y, shapeThickness, s/2+shapeThickness/2);
            break;
        case '2':
            parent.rect(x, y+(s/2)-(shapeThickness/2), s, shapeThickness);
            parent.rect(x+(s/2)-(shapeThickness/2), y+(s/2)-(shapeThickness/2), shapeThickness, s/2+shapeThickness/2);
            break;
        case '3':
            parent.rect(x, y+(s/2)-(shapeThickness/2), s/2+shapeThickness/2, shapeThickness);
            parent.rect(x+(s/2)-(shapeThickness/2), y, shapeThickness, s);
            break;
        case '0':
            parent.rect(x+(s/2)-(shapeThickness/2), y+(s/2)-(shapeThickness/2), shapeThickness, s/2+shapeThickness/2);
            parent.rect(x+(s/2)-(shapeThickness/2), y+(s/2)-(shapeThickness/2), s/2+shapeThickness/2, shapeThickness);
            break;
        case '.':
            parent.rect(x+(s/2)-(shapeThickness/2), y+(s/2)-(shapeThickness/2), shapeThickness, s/2+shapeThickness/2);
            parent.rect(x, y+(s/2)-(shapeThickness/2), s/2+shapeThickness/2, shapeThickness);
            break;
        default:
            parent.fill(baseColor);
            parent.rect(x, y, s, s);
            break;
        }
    }
    
//    
//    void saveCurrent() {
//        PImage temp = get(artworkStartX, 0, coverWidth, coverHeight);
//        if (filename.equals("")) {
//            temp.save("output/cover_" + currentBook + ".png");
//        } 
//        else {
//            temp.save("output/" + filename);
//        }
//    }
    
    
    public void keyPressed() {
        if (parent.key == ' ') {
            refresh = true;
            currentBook++;
        } 
        else if (parent.key == 's') {
            //saveCurrent();
        }
        else if (parent.key == PConstants.CODED) {
            refresh = true;
            if (parent.keyCode == PConstants.LEFT) {
                currentBook--;
            } 
            else if (parent.keyCode == PConstants.RIGHT) {
                currentBook++;
            }
        }
        if (currentBook >= books.length) {
            currentBook = 0;
        }
        if (currentBook < 0) {
            currentBook = books.length-1;
        }
    }
    
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "com.brett.processing.GeneratedCover" });
      }
}