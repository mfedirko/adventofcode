package com.adventofcode.day12;

import com.adventofcode.FileStreamSupport;

import java.awt.*;
import java.io.InputStream;

public class Day12 {
    public static void main(String[] args) {
        InputStream file = Day12.class.getResourceAsStream("/day12.txt");
        Ship pt1 = new Ship(Direction.E, new Point(0, 0));
        Ship pt2 = new Ship(new Point(0, 0), new Point(10, -1));
        FileStreamSupport.toStream(file)
                .forEach(line -> {
                    char instr = line.charAt(0);
                    int num = Integer.parseInt(line.substring(1));
                    switch (instr) {
                        case 'L':
                            num *= -1;
                            pt1.turn(num);
                            pt2.turnWaypoint(num);
                            break;
                        case 'R':
                            pt1.turn(num);
                            pt2.turnWaypoint(num);
                            break;
                        case 'F':
                            pt1.forward(num);
                            pt2.forwardToWaypt(num);
                            break;
                        case 'E':
                            pt1.east(num, pt1.pos);
                            pt2.east(num, pt2.waypoint);
                            break;
                        case 'W':
                            pt1.west(num, pt1.pos);
                            pt2.west(num, pt2.waypoint);
                            break;
                        case 'S':
                            pt1.south(num, pt1.pos);
                            pt2.south(num, pt2.waypoint);
                            break;
                        case 'N':
                            pt1.north(num, pt1.pos);
                            pt2.north(num, pt2.waypoint);
                            break;
                    }
                });

        System.out.printf("Pt1: %d\n", pt1.manhattanDistance());
        System.out.printf("Pt2: %d\n", pt2.manhattanDistance());
    }

    private enum Direction {
        E(0),
        S(90),
        W(180),
        N(270);

        private int angleDeg;
        Direction(int angleDeg) {
            this.angleDeg = angleDeg;
        }

        public Direction turn(int angleDeg) {
            int angle = this.angleDeg + angleDeg;
            if (angle < 0) angle += 360;
            if (angle >= 360) angle = angle % 360;
            return fromAngle(angle);
        }

        public static Direction fromAngle(int angleDeg) {
            for (Direction dir : values()) {
                if (dir.angleDeg == angleDeg) {
                    return dir;
                }
            }
            return null;
        }
    }

    private static class Ship {
        private Direction dir;
        private Point pos;
        private Point waypoint;

        Ship(Direction dir, Point pos) {
            this.dir = dir;
            this.pos = pos;
        }
        Ship(Point pos, Point waypt) {
            this.pos = pos;
            this.waypoint = waypt;
        }

        int manhattanDistance() {
            return Math.abs(pos.x) + Math.abs(pos.y);
        }

        void turn(int num) {
            this.dir = this.dir.turn(num);
        }
        void turnWaypoint(int num) {
            int newX = cos(waypoint.x, num) - sin(waypoint.y, num);
            int newY = cos(waypoint.y, num) + sin(waypoint.x, num);
            waypoint.x = newX;
            waypoint.y = newY;
        }
        private int cos(int r, int deg) {
            return (int)Math.round(r * Math.cos(Math.toRadians(deg)));
        }
        private int sin(int r, int deg) {
            return (int)Math.round(r * Math.sin(Math.toRadians(deg)));
        }

        void forwardToWaypt(int num) {
            this.pos.x += num * waypoint.x;
            this.pos.y += num * waypoint.y;
        }
        private void forward(int num) {
            switch (dir) {
                case E:
                    east(num, pos);
                    break;
                case N:
                    north(num, pos);
                    break;
                case S:
                    south(num, pos);
                    break;
                case W:
                    west(num, pos);
                    break;
            }
        }
        void east(int num, Point pos) {
            pos.translate(num, 0);
        }
        void south(int num, Point pos) {
            pos.translate(0, num);
        }
        void west(int num, Point pos) {
            pos.translate(-num, 0);
        }
        void north(int num, Point pos) {
            pos.translate(0, -num);
        }


    }
}
