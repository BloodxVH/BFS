import edu.princeton.cs.algs4.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstPaths {
//    public static boolean[] marked;
//    public static int[] edgeTo;
//    private final int s; // source vertex
	
//	Dòng đầu tiên và thứ hai là các lệnh import, chúng cho phép bạn sử dụng các lớp từ các gói khác nhau trong Java. Cụ thể, bạn đang import các lớp từ gói edu.princeton.cs.algs4, và các lớp cho việc xử lý file và cấu trúc dữ liệu hàng đợi.
//
//	Dòng tiếp theo là khai báo lớp BreadthFirstPaths.  
    private boolean[] marked;
    private int[] edgeTo;
    private final int s; // source vertex
 //   Ở đây, bạn đang khai báo các biến instance marked, edgeTo, và s
    //. Biến marked là một mảng boolean để đánh dấu các đỉnh đã được duyệt qua trong quá trình duyệt BFS. Biến edgeTo là một mảng chứa đỉnh trước đỉnh hiện tại trong đường đi ngắn nhất từ đỉnh nguồn đến một đỉnh khác. Biến s là đỉnh nguồn.

    public BreadthFirstPaths(myGraph G, int s, int des, String[] vertexNames, PrintWriter writer) {
//  mĐây là phương thức khởi tạo của lớp BreadthFirstPaths. Nó nhận các tham số là đồ thị G, đỉnh nguồn s, đỉnh đích des, một mảng chứa tên của các đỉnh, và một đối tượng PrintWriter để ghi thông tin ra file.
    
    	marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        myBFS(G, s, des, vertexNames, writer);
        bfs(G, s);
    }
  //  Trong phương thức khởi tạo, bạn khởi tạo mảng marked và edgeTo với kích thước bằng số lượng đỉnh trong đồ thị G, sau đó gọi phương thức myBFS và bfs để thực hiện tìm kiếm theo chiều rộng trên đồ thị.
 
    public void myBFS(myGraph G, int source, int des, String[] vertexNames, PrintWriter writer) {
    	
    		writer.printf("Phat trien trang thai | Trang thai ke | Danh sach L");
    		writer.printf("\n");
//	Đây là phương thức myBFS, thực hiện tìm kiếm theo chiều rộng trên đồ thị, với tham số là đồ thị G, đỉnh nguồn source, đỉnh đích des, mảng chứa tên các đỉnh, và đối tượng PrintWriter để ghi thông tin ra file.     
            Queue<Integer> queue = new LinkedList<>();
            marked[source] = true;
            queue.add(source);
//   Bạn khởi tạo một hàng đợi và đánh dấu đỉnh nguồn là đã duyệt qua, sau đó thêm nó vào hàng đợi.          
            while (!queue.isEmpty()) {
            	int s = 0;
            	int d = 0;
                int v = queue.poll();
                writer.printf("           " + vertexNames[v] + "          | ");
                for (int w : G.adj(v)) {
                	if (!marked[w]) {
                		writer.printf(vertexNames[w] + " ");
                		s++;
                    }           	
                }
                s = s*2;
                d = 15 - s;
                for(int i=1; i<d; i++) {
                	writer.printf(" ");
                }
                writer.printf("| ");
// Bạn thực hiện lặp qua các đỉnh kề của đỉnh hiện tại và ghi thông tin về chúng ra file.
                for (int w : G.adj(v)) {       	
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        marked[w] = true;
                        queue.add(w);
                    }
                }
                for (int item : queue) {
                    writer.print(vertexNames[item] + " ");
                }    
                writer.println();
                if(v == des) {
                	break;
                }
            } 
    	
//   Trong vòng lặp này, bạn duyệt qua các đỉnh kề của đỉnh hiện tại và đánh dấu chúng đã được duyệt qua, sau đó thêm chúng vào hàng đợi.  	
    }
    private void bfs(myGraph G, int s) {
        Queue<Integer> queue = new LinkedList<>();
        marked[s] = true;
        queue.add(s);
// Phương thức bfs thực hiện tìm kiếm theo chiều rộng trên đồ thị, với tham số là đồ thị G và đỉnh nguồn s.
        while (!queue.isEmpty()) {
            int v = queue.poll();         
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.add(w);
                }
            }
            
        }
    }
  //  Trong phương thức bfs, bạn duyệt qua các đỉnh kề của đỉnh hiện tại và đánh dấu chúng đã được duyệt qua, sau đó thêm chúng vào hàng đợi.
    public boolean hasPathTo(int v) {
        return marked[v];
    }
  //  Phương thức hasPathTo kiểm tra xem có đường đi từ đỉnh nguồn đến đỉnh v không.
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;

        LinkedList<Integer> path = new LinkedList<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.addFirst(x);
        }
        path.addFirst(s);

        return path;
    }
    
    
   // Phương thức pathTo trả về một đường đi từ đỉnh nguồn đến đỉnh v.
    public void printPath(myGraph G, int source, int destination, String[] vertexNames, PrintWriter writer) {
        if (hasPathTo(destination)) {
            writer.printf("Đường đi %s đến %s:  ", vertexNames[source], vertexNames[destination].charAt(0));
            for (int x : pathTo(destination)) {
                writer.print(vertexNames[x] + " ");
            }
            writer.println();
        } else {
            writer.printf("%s to %c: not connected\n", vertexNames[source], vertexNames[destination].charAt(0));
        }
    }
    

  //  Phương thức printPath ghi đường đi từ đỉnh nguồn đến đỉnh đích ra file dựa trên kết quả của phương thức pathTo. Nếu không có đường đi nào, nó sẽ in ra thông báo "not connected".
   
    public static void main(String[] args) {
    	
//    	boolean[] marked;
//        int[] edgeTo;
//        int s; // source vertex
    	
            if (args.length < 1) {
                StdOut.println("Sử dụng: java BreadthFirstPaths <tệp_nhập>");
                return;
            }

            In in = new In(args[0]);
            myGraph G = new myGraph(in);

            // Đọc số lượng đỉnh và tên đỉnh
            int numVertices = G.V();
            String[] vertexNames = G.getVertexNames();

            // Đọc đỉnh nguồn và đỉnh đích
            String sourceVertex = in.readString();
            String destinationVertex = in.readString();

            int sourceIndex = -1, destinationIndex = -1;

            // Lấy chỉ số của đỉnh nguồn và đỉnh đích
            for (int i = 0; i < numVertices; i++) {
                if (vertexNames[i].equals(sourceVertex)) {
                    sourceIndex = i;
                } else if (vertexNames[i].equals(destinationVertex)) {
                    destinationIndex = i;
                }

                if (sourceIndex != -1 && destinationIndex != -1) {
                    break;
                }
            }

            if (sourceIndex == -1 || destinationIndex == -1) {
                StdOut.println("Đỉnh nguồn hoặc đỉnh đích không hợp lệ.");
                return;
            }
            
            
            try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))){
            	// in bảng
            	BreadthFirstPaths myBFS = new BreadthFirstPaths(G, sourceIndex, destinationIndex,vertexNames, writer);
            	
            	// in đường
            	myBFS.printPath(G, sourceIndex, destinationIndex, vertexNames, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
                
    	
      }   
}
