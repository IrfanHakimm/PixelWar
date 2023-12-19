package main;

import monster.Slime;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setSlime() {

        int[] worldXCoordinates = { 23, 29, 37, 36, 35, 32, 40 };
        int[] worldYCoordinates = { 36, 20, 27, 35, 19, 28, 26 };

        for (int i = 0; i < 7; i++) {
            gp.slime[i] = new Slime(gp);
            gp.slime[i].worldX = gp.tileSize * worldXCoordinates[i];
            gp.slime[i].worldY = gp.tileSize * worldYCoordinates[i];
        }

    }
}
