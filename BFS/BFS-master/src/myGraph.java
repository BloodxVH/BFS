import edu.princeton.cs.algs4.*;
import java.util.NoSuchElementException;

public class myGraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
  //  Trong lớp myGraph, private Bag<Integer>[] adj được sử dụng để lưu trữ danh sách các đỉnh kề với mỗi đỉnh trong đồ thị.
  //   nó được sử dụng để lưu trữ các đỉnh kề của mỗi đỉnh trong đồ thị. Mỗi phần tử trong mảng adj là một túi (bag) chứa danh sách
    // các đỉnh kề của một đỉnh cụ thể.
    private Bag<Integer>[] adj;
    private String[] vertexNames;
    //Lớp myGraph đại diện cho một đồ thị vô hướng không có hướng. Các biến instance bao gồm:
    //	V: số lượng đỉnh trong đồ thị.
   // 	E: số lượng cạnh trong đồ thị.
    //	adj: một mảng của các túi (bags), mỗi túi chứa danh sách các đỉnh kề với một đỉnh cụ thể.
   // 	vertexNames: một mảng chứa tên của các đỉnh trong đồ thị.
    
    
    //Phương thức khởi tạo, nhận số lượng đỉnh và một mảng tên đỉnh, và tạo ra một đồ thị với các thuộc tính đã được khởi tạo.
    public myGraph(int V, String[] vertexNames) {
        if (V < 0 || V != vertexNames.length)
            throw new IllegalArgumentException("Invalid number of vertices or vertexNames array length mismatch");
        this.V = V;
        this.E = 0;
        this.vertexNames = vertexNames.clone();
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }
    //Phương thức khởi tạo khác, nhận một đối tượng In 
    //(để đọc từ một luồng đầu vào) và tạo đồ thị từ dữ liệu trong luồng này.
    // đọc đồ thị từ txt
    public myGraph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
            this.vertexNames = new String[V];
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                vertexNames[v] = in.readString();
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be non-negative");
            for (int i = 0; i < E; i++) {
                String v = in.readString();
                String w = in.readString();
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    // đọc đồ thị
//    public myGraph(myGraph G) {
//        this.V = G.V();
//        this.E = G.E();
//        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
//
//        // update adjacency lists
//        adj = (Bag<Integer>[]) new Bag[V];
//        for (int v = 0; v < V; v++) {
//            adj[v] = new Bag<Integer>();
//        }
//
//        for (int v = 0; v < G.V(); v++) {
//            // reverse so that adjacency list is in same order as original
//            Stack<Integer> reverse = new Stack<Integer>();
//            for (int w : G.adj[v]) {
//                reverse.push(w);
//            }
//            for (int w : reverse) {
//                adj[v].add(w);
//            }
//        }
//    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

   // Phương thức này kiểm tra xem một đỉnh có hợp lệ hay không (nằm trong phạm vi từ 0 đến V-1).
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }
//    Phương thức này thêm một cạnh giữa hai đỉnh v và w vào đồ thị.
    public void addEdge(String v, String w) {
        int indexV = indexOfVertex(v);
        int indexW = indexOfVertex(w);
        if (indexV == -1 || indexW == -1) {
            throw new IllegalArgumentException("Invalid vertex names: " + v + ", " + w);
        }
        addEdge(indexV, indexW);
    }
    
    private int indexOfVertex(String vertexName) {
        for (int i = 0; i < V; i++) {
            if (vertexNames[i].equals(vertexName)) {
                return i;
            }
        }
        return -1;
    }
    // Phương thức này tìm chỉ số của một đỉnh dựa trên tên đỉnh. 
    public String[] getVertexNames() {
        return vertexNames.clone();
    }
    //Phương thức này trả về một Iterable chứa tất cả các đỉnh kề với một đỉnh cụ thể.
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        s.append(V + " vertices, " + E + " edges " + NEWLINE);
//        for (int v = 0; v < V; v++) {
//            s.append(vertexNames[v] + ": ");
//            for (int w : adj[v]) {
//                s.append(vertexNames[w] + " ");
//            }
//            s.append(NEWLINE);
//        }
//        return s.toString();
//    }
    // Phương thức main được sử dụng để kiểm tra lớp myGraph bằng cách đọc dữ liệu từ một tệp và in ra đồ thị đã được tạo.
    public static void main(String[] args) {
        In in = new In(args[0]);
        myGraph G = new myGraph(in);
        StdOut.println(G);
    }

}

/******************************************************************************
 *  Copyright 2002-2022, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
