/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.service;
import campus_app.exceptions.InvalidPriceException;
import campus_app.exceptions.InvalidValueException;
import dataStructures.*;
import campus_app.app.Position;

public abstract class ServiceAbstract implements Service {
    private final String name;
    private final Position position;
    private final int price;
    private final int value;
    int rating;
    List<String> ratings;
    protected ServiceAbstract(String serviceName, Position position, int price, int value, ServiceType type) throws InvalidPriceException, InvalidValueException {
        if(price < 0) {
            throw new InvalidPriceException(type);
        }
        if(value < 0) {
            throw new InvalidValueException(type);
        }
        this.name = serviceName;
        this.price = price;
        this.value = value;
        this.rating = 4;
        this.position = position;
        this.ratings = new DoublyLinkedList<>();
    }

    @Override
    public void addRating(int rating, String description) {
        this.rating+=rating;
        String formatted = " "+description+" ";
        ratings.addLast(formatted); // O(1)
    }

    @Override
    public int getRating() {
        return Math.round(this.getRealRating());
    }

    private float getRealRating() {
        return (float)rating / (ratings.size()+1);
    }

    @Override
    public int getPrice() {
        return price;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position getPosition() {
        return position;
    }
    protected int getValue() {
        return value;
    }

    /**
     * Checks if a service has a comment with the given tag
     * O(n)
     * @param tagName the tag to check
     * @return whether the service has a comment with the given tag
     */
    @Override
    public boolean hasTag(String tagName) {
        String formated = " "+tagName+" ";
        char[] pattern = formated.toCharArray();

        Iterator<String> iter = ratings.iterator();
        int position = -1;
        while(iter.hasNext() && position<0) {
            char[] text = iter.next().toCharArray();
            position = findPattern(text, pattern);
        }
        return position>0;
    }
    private int[] computeFail(char[] pattern){
        int n = pattern.length;
        int[] failTable = new int[n];
        int j = 1;
        int k = 0;
        while(j < n){
            if(pattern[j]==pattern[k]){
                failTable[j] = k+1;
                j++;
                k++;
            } else if(k>0)
                k = failTable[k-1];
            else j++;
        }return failTable;
    }
    private int findPattern(char[]text, char[]pattern){
        int n = text.length;
        int m = pattern.length;
        if(m==0)return 0;
        int[] failTable = computeFail(pattern);
        int j = 0;
        int k = 0;
        while (j < n) {
            if(text[j] == pattern[k]){
                if(k== m-1)return j=m+1; //match found
                j++;
                k++;
            }else if(k>0)
                k = failTable[k-1];
            else j++;
        }return -1;//not found
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Service service)) {
            return false;
        }
        return this.getName().equalsIgnoreCase(service.getName());
    }
}
