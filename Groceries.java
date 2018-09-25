package cs445.a1;
/**
 * A Groceries is a representation of a shopping "list", though it is unordered
 * and does not permit duplicates. It uses the Set data structure to store a
 * collection of GroceryItems.
 */
public class Groceries implements GroceriesInterface {

    //set of groceryitem
    Set<GroceryItem> groceryItems;



    //constructors
    public Groceries(){
        groceryItems = new Set<>();
    }

    /**
     * Adds item to these groceries. If an item with the same description is
     * already in these groceries, then its quantity is increased by the amount
     * specified. For instance, if 2 bananas are added when 3 bananas are in the
     * groceries, the quantity is increased to 5 bananas. If an item with the
     * same description is not in these groceries, then it is added with the
     * specified quantity.
     *
     * @param item the item to add
     */
    public void addItem(GroceryItem item){
        int changedFrom = changeItem(item, item.getQuantity());
        if(changedFrom == -1){
            //does not contain. so add a new one
            try{
                groceryItems.add(item);
            }catch(SetFullException e){
                e.getMessage();
            }
            
        }
    }

    /**
     * Removes item from these groceries. If an item with the same description
     * is in these groceries, then its quantity is decreased by the amount
     * specified. For instance, if 3 bananas are removed when 10 bananas are in
     * the groceries, the quantity is decreased to 7 bananas. If the specified
     * quantity is greater than or equal to the current quantity, the item is
     * removed entirely. If an item with the same description is not in these
     * groceries, this method does nothing.
     *
     * @param item the item to remove
     */
    public void removeItem(GroceryItem item){
        try{
            int changedFrom = changeItem(item, (item.getQuantity()*-1));
        }catch(IllegalArgumentException e){
            //it is 0 or negative quantity
            groceryItems.remove(item);
        }
    }

    /**
     * Modifies an item's quantity within these groceries. If an item with the
     * same decsription is in these groceries, then its quantity is modified to
     * match the amount specified, and the previous quantity is returned. If an
     * item with the same description is not in these groceries, this method
     * makes no changes and returns -1.
     *
     * @param item the item to modify in the set
     * @return  the old quantity, or -1 if the item was not found
     */
    public int modifyQuantity(GroceryItem item){
        int toReturn = -1;
        if(groceryItems.contains(item)){
            GroceryItem toModify = groceryItems.remove(item);
            toReturn = toModify.getQuantity();
            toModify.setQuantity(item.getQuantity());
            try{
                groceryItems.add(toModify);
            }catch(SetFullException e){
                e.getMessage();
                toReturn = -1;
            }
        }
        return toReturn;

    }
    //used by both add and remove. will take original amount and add new amount (negative if remove)
    private int changeItem(GroceryItem item, int amount) throws IllegalArgumentException{
        //returns original quantity. if does not contain, returns -1
        int toReturn = -1;
        if(groceryItems.contains(item)){
            GroceryItem toModify = groceryItems.remove(item);
            toReturn = toModify.getQuantity();
            toModify.setQuantity(toModify.getQuantity()+amount);
            try{
                groceryItems.add(toModify);
            }catch(SetFullException e){
                e.getMessage();
                toReturn = -1;
            }
        }
        return toReturn;
    }

    /**
     * Prints all groceries. Includes a header "Groceries:" and prints each item
     * on a separate line.
     */
    public void printAll(){
        Object[] array = groceryItems.toArray();
        System.out.println("Groceries:");
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]);
        }
    }

}

