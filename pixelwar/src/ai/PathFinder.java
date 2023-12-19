package ai;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class PathFinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, curNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNode();
    }

    public void instantiateNode() {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row] = new Node(col, row);

            col++;
            if (col == gp.maxWorldCol) {

                col = 0;
                row++;
            }
        }
    }

    public void resetNode() {
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == gp.maxWorldCol) {

                col = 0;
                row++;
            }
        }
        // reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
        resetNode();

        // set start and goal node
        startNode = node[startCol][startRow];
        curNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(curNode);

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            // set solid node
            // Check Tiles
            int tileNum = gp.tm.mapTileNum[col][row];
            if (gp.tm.tile[tileNum].collision == true) {
                node[col][row].solid = true;
            }
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node) {
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (goalReached == false && step < 500) {
            int col = curNode.col;
            int row = curNode.row;

            curNode.checked = true;
            openList.add(curNode);

            // open up node
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);

            }
            // open left node
            if (row - 1 >= 0) {
                openNode(node[col - 1][row]);

            }
            // open down node
            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]);

            }
            // open right node
            if (row + 1 < gp.maxWorldCol) {
                openNode(node[col + 1][row]);

            }

            int bestNodeIndex = 0;
            int bestNodefCost = 0;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            if (openList.size() == 0) {
                break;
            }

            curNode = openList.get(bestNodeIndex);
            if (curNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node) {
        if (node.open == false && node.checked == false && node.solid == false) {
            node.open = true;
            node.parent = curNode;
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;
        while (current != startNode) {

            pathList.add(0, current);

            current = current.parent;
        }
    }
}
