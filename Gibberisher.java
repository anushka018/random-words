//Name: Anushka Angamuthu, x500:angam003
/**Class that implements a Gibberisher object which generates random words*/

public class Gibberisher {
    private Trie<CharBag> model;
    private int segmentLength;
    private int numSamples;

    /**
     * Initializes a Gibberisher object
     * @param segmentLength int the length of "chunks" that the LetterSample objects will be
     */
    public Gibberisher (int segmentLength) {
        this.segmentLength = segmentLength;
        numSamples = 0;
        model = new Trie<>();
    }

    /**
     * Adds one sample into the model
     * @param sample LetterSample the sample to be added
     */
    public void train(LetterSample sample) {
        CharBag bag = model.get(sample.getSegment());
        if (bag == null) {
            CharBag segment = new CharBag();
            segment.add(sample.getNextLetter());
            model.put(sample.getSegment(), segment);
        }
        else {
            bag.add(sample.getNextLetter());
        }
        numSamples++;
    }

    /**
     * Generates LetterSamples and trains them using the preceding function
     * @param str String the word which LetterSamples will be generated from
     */
    public void train(String str) {
        LetterSample [] samples = LetterSample.toSamples(str, segmentLength);
        for (int i = 0; i < samples.length ; i++) {
            train(samples[i]);
        }
    }

    /**
     * Adds an array of Strings to the model using the two preceding functions
     * @param strs String[] an array of Strings to be added to the model
     */
    public void train(String[] strs) {
        for (int i = 0; i < strs.length ; i++) {
            train(strs[i]);
        }
    }

    /**
     * Gets the number of samples stored in the model
     * @return int the number of samples
     */
    public int getSampleCount () {
        return numSamples;
    }

    /**
     * Generates random words by implementing Trie and CharBag data structures
     * @return String a random word that was generated by the algorithm
     */
    public String generate () {
        String word = "";
        String sample;
        while (word.length() == 0 || word.charAt(word.length()-1) != LetterSample.STOP) {
            if (word.length() < segmentLength){
                sample = word;
            }
            else {
                sample = word.substring(word.length()- segmentLength);
            }
            CharBag bag = model.get(sample);
            char nextLetter = bag.getRandomChar();
            word += nextLetter;
        }
        word = word.substring(0,word.length()-1);
        return word;
    }

}