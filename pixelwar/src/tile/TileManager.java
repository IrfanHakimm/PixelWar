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
	Tile[] tile;
	int mapTileNum[][];

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[15];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		loadMap();

	}

	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gtepi.png"));
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_shadow_kiri.png"));
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gtepi_shadow_kiri.png"));
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/map/map.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();

				while (col < gp.maxScreenCol) {
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void draw(Graphics2D g2) {
		// g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);

		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

			int tileNum = mapTileNum[col][row];

			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
	}
}
