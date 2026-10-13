
public class inventory {
    private final int[] itemID = new int[10];
    private final boolean[] slotsUsed = new boolean[itemID.length];

    public boolean hasSword = true;
    public int swordDurability = 15;

    private helperFuncs helper = new helperFuncs();

    public inventory() {
        itemID[0] = 1;
        slotsUsed[0] = true;
    }

    public void addItem(int itemID, int slot) {
        if (slot < 0 || slot >= this.itemID.length) {
            throw new IndexOutOfBoundsException(
                "Invalid inventory slot: " + slot
            );
        }

        this.itemID[slot] = itemID;
        this.slotsUsed[slot] = true;
    }


    public void useSword(boolean attackSuccessful) {
        if (!hasSword) {
            helper.SOPL("You don't have a sword to use.");
            return;
        }

        if (attackSuccessful) {
            swordDurability--;

            if (swordDurability <= 0) {
                hasSword = false;
                helper.SOPL("Your sword has broken!");
            }
        }
    }

    public void SetID(int ID) {
        for (int i = 0; i < itemID.length; i++) {
            if (!slotsUsed[i]) {
                itemID[i] = ID;
                slotsUsed[i] = true;
                return;
            } else if (slotsUsed[i] && itemID[i] == ID) {
                helper.SOPL("Item already exists in the inventory.");
                return;
            } else {
                helper.SOPL("Slot " + i +
                    " is already occupied by item ID: " + itemID[i]);
            }
        }
    }

    public int getItemID(int slot) {
        return this.itemID[slot];
    }
}
