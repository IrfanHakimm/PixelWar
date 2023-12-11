package main;

import monster.Slime;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setSlime() {
        
            gp.slime[0] = new Slime(gp);
            gp.slime[0].worldX = gp.tileSize * 23;
            gp.slime[0].worldY = gp.tileSize * 36;

            gp.slime[1] = new Slime(gp);
            gp.slime[1].worldX = gp.tileSize * 29;
            gp.slime[1].worldY = gp.tileSize * 20;

    }

}
