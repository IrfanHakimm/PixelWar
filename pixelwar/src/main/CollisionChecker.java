package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;

    }

    public void CheckTileForPlayer(Entity entity) {
        int entityLeftWorldX_player = entity.worldX + entity.solidArea.x + 32;
        int entityRightWorldX_player = entity.worldX + entity.solidArea.x + 58;
        int entityTopWorldY_player = entity.worldY + entity.solidArea.y + 64;
        int entityBottomWorldY_player = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol_player = entityLeftWorldX_player / gp.tileSize;
        int entityRightCol_player = entityRightWorldX_player / gp.tileSize;
        int entityTopRow_player = entityTopWorldY_player / gp.tileSize;
        int entityBottomRow_player = entityBottomWorldY_player / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow_player = (entityTopWorldY_player - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol_player][entityTopRow_player];
                tileNum2 = gp.tm.mapTileNum[entityRightCol_player][entityTopRow_player];
                if (gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow_player = (entityBottomWorldY_player + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol_player][entityBottomRow_player];
                tileNum2 = gp.tm.mapTileNum[entityRightCol_player][entityBottomRow_player];
                if (gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol_player = (entityLeftWorldX_player - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol_player][entityTopRow_player];
                tileNum2 = gp.tm.mapTileNum[entityLeftCol_player][entityBottomRow_player];
                if (gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol_player = (entityRightWorldX_player + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityRightCol_player][entityTopRow_player];
                tileNum2 = gp.tm.mapTileNum[entityRightCol_player][entityBottomRow_player];
                if (gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true) {

                    entity.collisionOn = true;
                }
        }
    }

    public void CheckTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tm.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true) {

                    entity.collisionOn = true;
                }
        }
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // get entity's solid area position
                gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
                gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

                // get target's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (target[i].solidArea.intersects(target[i].solidArea)) {
                    if (entity.solidArea.intersects(target[i].solidArea) && target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;
        // get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // get target's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;

                break;
            case "down":
                entity.solidArea.y += entity.speed;

                break;
            case "left":
                entity.solidArea.x -= entity.speed;

                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}