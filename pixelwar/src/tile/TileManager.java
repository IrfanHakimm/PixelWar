package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	public boolean drawPath = true;

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[15];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap();

	}

	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gtepi.png"));
			tile[1].collision = true;
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_shadow_kiri.png"));
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gtepi_shadow_kiri.png"));
			tile[3].collision = true;
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png"));
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rtepi.png"));
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock_shadow_kiri.png"));
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock_light_kanan.png"));
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rtepi_shadow_kiri.png"));
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_shadow_atas.png"));
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_shadow_kanan.png"));
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_shadow_atas_kanan.png"));
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lava_shadow_atas.png"));
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lava_shadow_atas_kanan.png"));

			for (int i = 9; i <= 12; i++) {
				tile[i].collision = true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/map.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();

				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {

		int col = 0;
		int row = 0;

		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

			int tileNum = mapTileNum[col][row];

			int worldX = col * gp.tileSize;
			int worldY = row * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			}

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}

		}
	}
}