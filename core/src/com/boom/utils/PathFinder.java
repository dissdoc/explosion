package com.boom.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class PathFinder {

    public static Stack<Cell> getPath(TileType[][] map, Cell hero, Cell cursor) {
        Map<Cell, List<Cell>> graph = getGraph(map, cursor);
        Map<Cell, Cell> visited = bfs(hero, cursor, graph);

        return getShortPath(visited, hero, cursor);
    }

    private static Map<Cell, List<Cell>> getGraph(TileType[][] map, Cell cursor) {
        Map<Cell, List<Cell>> data = new HashMap<>();

        if (!checkBoard(map, cursor) || map[cursor.x][cursor.y] == TileType.Door ||
                map[cursor.x][cursor.y] == TileType.Floor)
            return data;

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                TileType type = map[x][y];

                if (type == TileType.Floor || type == TileType.Door)
                    continue;

                data.put(new Cell(x, y), getNextNodes(map, x, y));
            }
        }

        return data;
    }

    private static Map<Cell, Cell> bfs(Cell hero, Cell cursor, Map<Cell, List<Cell>> graph) {
        Queue<Cell> queue = new LinkedList<>();
        queue.add(hero);
        Map<Cell, Cell> visited = new LinkedHashMap<>();

        while (!queue.isEmpty()) {
            Cell node = queue.remove();


            List<Cell> nextNodes = graph.get(node);
            if (nextNodes == null)
                continue;

            for (Cell next : nextNodes) {
                if (visited.containsKey(next))
                    continue;
                queue.add(next);
                visited.put(next, node);
            }

            if (node.x == cursor.x && node.y == cursor.y) {
                cursor.direction = node.direction;
                return visited;
            }
        }

        return visited;
    }

    private static Stack<Cell> getShortPath(Map<Cell, Cell> visited, Cell hero, Cell cursor) {
        Stack<Cell> path = new Stack<>();

        if (visited == null || visited.size() == 0)
            return path;

        path.add(cursor);
        while (!cursor.equals(hero)) {
            cursor = visited.get(cursor);

            if (cursor == null)
                break;

            path.add(cursor);
        }
        path.pop();

        return path;
    }

    private static List<Cell> getNextNodes(TileType[][] map, int x, int y) {
        List<Cell> nodes = new LinkedList<>();

        Cell left = new Cell(x - 1, y);
        if (checkBoard(map, left) && checkHNode(map, new Cell(left.x, left.y + 1))) {
            left.direction = Direction.LEFT;
            nodes.add(left);
        }

        Cell right = new Cell(x + 1, y);
        if (checkBoard(map, right) && checkHNode(map, new Cell(right.x, right.y + 1))) {
            right.direction = Direction.RIGHT;
            nodes.add(right);
        }

        Cell bottom = new Cell(x, y + 1);
        if (checkBoard(map, bottom) && (checkVNode(map, bottom)
                || checkVNode(map, new Cell(bottom.x, bottom.y + 1)))) {
            bottom.direction = Direction.BOTTOM;
            nodes.add(bottom);
        }

        Cell top = new Cell(x, y - 1);
        if (checkBoard(map, top) && checkVNode(map, top)) {
            top.direction = Direction.TOP;
            nodes.add(top);
        }

        return nodes;
    }

    private static boolean checkBoard(TileType[][] map, Cell point) {
        return (point.x >= 0 && point.x < map.length) && (point.y >= 0 && point.y < map[0].length);
    }

    private static boolean checkVNode(TileType[][] map, Cell point) {
        if (!checkBoard(map, point))
            return false;

        return map[point.x][point.y] == TileType.Ladder;
    }

    private static boolean checkHNode(TileType[][] map, Cell point) {
        if (!checkBoard(map, point))
            return false;

        TileType type = map[point.x][point.y];

        return type == TileType.Ladder || type == TileType.Floor;
    }

    public static class Cell {
        public int x;
        public int y;
        public Direction direction;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Cell))
                return false;

            Cell other = (Cell) o;

            return other.x == this.x && other.y == this.y;
        }

        @Override
        public int hashCode() {
            return String.format("[%s:%s]", x, y).hashCode();
        }
    }

    public enum Direction {
        LEFT, RIGHT, TOP, BOTTOM
    }
}
