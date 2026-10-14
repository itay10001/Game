public class inventory {
    private final int[] itemID = new int[10];
    private final boolean[] slotsUsed = new boolean[itemID.length];

    public boolean hasSword = true;
    public int swordDurability = 15;
    public int mpAmount = 3;
    public int HpAmount = 3;

    private final helperFuncs helper = new helperFuncs();

    public inventory() {
        itemID[0] = 1; // Sword
        slotsUsed[0] = true;
        itemID[1] = 2; // Mana potion stack
        slotsUsed[1] = true;
        itemID[2] = 3; // Health potion
        slotsUsed[2] = true;
    }

    public void addItem(int itemID, int slot) {
        if (slot < 0 || slot >= this.itemID.length) {
            throw new IndexOutOfBoundsException("Invalid inventory slot: " + slot);
        }

        this.itemID[slot] = itemID;
        this.slotsUsed[slot] = true;
    }

    public double useManaPotion(double mana) {
        if (!hasMPquestion()) {
            helper.SOPL("No mana potions left.");
            return mana;
        }
        if (mana >= 100.0) {
            helper.SOPL("Your mana is already full.");
            return mana;
        }

        mpAmount--;
        double newMana = Math.min(100.0, mana + 10.0);

        if (mpAmount == 0) {
            itemID[1] = 0;
            slotsUsed[1] = false;
        }
        return newMana;
    }

    public int useHealthPotion(int hp) {
        if (!hasHPquestion()) {
            helper.SOPL("No health potions left.");
            return hp;
        }
        if (hp >= 100.0) {
            helper.SOPL("Your hp is already full.");
            return hp;
        }

        HpAmount--;
        int newHp = Math.min(100, hp + 10);

        if (HpAmount == 0) {
            itemID[2] = 0;
            slotsUsed[2] = false;
        }
        return newHp;
    }

    public int RepairSword(int newDurability){
        swordDurability = swordDurability + newDurability;
        if(swordDurability > 0){
            hasSword = true;
        }
        return swordDurability;
    }








    public boolean hasMPquestion() {
        return mpAmount > 0;
    }

    public boolean hasHPquestion() {
        return HpAmount > 0;
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
            if (slotsUsed[i] && itemID[i] == ID) {
                helper.SOPL("Item already exists in the inventory.");
                return;
            }
        }
        for (int i = 0; i < itemID.length; i++) {
            if (!slotsUsed[i]) {
                itemID[i] = ID;
                slotsUsed[i] = true;
                return;
            }
        }
        helper.SOPL("Inventory is full.");
    }

    public int getItemID(int slot) {
        return this.itemID[slot];
    }
}
