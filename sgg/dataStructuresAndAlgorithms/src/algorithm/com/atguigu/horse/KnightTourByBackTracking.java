package algorithm.com.atguigu.horse;

class MyList{
	public int size = 0;
	public int currentIndex = 0;
	//保存能到达的坐标
	int[][] arr = new int[8][2];

	public void add(int x,int y){
		arr[size][0] = x;
		arr[size][1] = y;
		size++;
	}

	public int[] get(int i){
		return arr[i];
	}

	public void sort(){
		for(int k = 0;k < this.size - 1;k++) {
			for (int i = 0; i < this.size - 1 - k; i++) {
				int row = arr[i][0];
				int col = arr[i][1];
				int nrow = arr[i + 1][0];
				int ncol = arr[i + 1][1];
				if (KnightTourByBackTracking.mmm[row][col].size > KnightTourByBackTracking.mmm[nrow][ncol].size) {
					arr[i + 1][0] = row;
					arr[i + 1][1] = col;
					arr[i][0] = nrow;
					arr[i][1] = ncol;
				}
			}
		}
	}
}

/**
 * 	使用回溯法(暴力匹配法)解决 骑士的周游问题。
 * @author Administrator
 *
 */
public class KnightTourByBackTracking {
	private int N;
	private int[][] chessboard;
	private int sourceRow;
	private int sourceColumn;
	// 【注意】 xMove 和 yMove 是固定的，因为骑士固定是走日。所以理论上，最多只能有 8 种走法。
	//       这两个数组的组合顺序，影响着遍历的顺序。
	private int[] xMove = {2, 1, -1, -2, -2, -1, 1, 2};
	private int[] yMove = {1, 2, 2, 1, -1, -2, -2, -1};

	public static MyList[][] mmm = new MyList[8][8];


	/**
	 * 构造函数，初始化棋盘和起点
	 * 
	 * @param n            棋盘的大小
	 * @param sourceRow    起点的行索引
	 * @param sourceColumn 起点的列索引
	 */
	public KnightTourByBackTracking(int n, int sourceRow, int sourceColumn) {
		if (n < 4) {
			throw new RuntimeException("棋盘大小不能小于 4 * 4");
		}
		if (sourceRow < 0 || sourceColumn < 0) {
			throw new RuntimeException("sourceRow 和 sourceColumn 不能小于零！");
		}

		N = n;
		this.sourceRow = sourceRow;
		this.sourceColumn = sourceColumn;

		chessboard = new int[N][N];
		// 首先，我们让棋盘的全部元素都初始化成 -1, 表示这个位置没有遍历过
		for (int x = 0; x < chessboard.length; x++) {
			for(int y = 0; y < chessboard[0].length; y++){
				MyList myQueue = new MyList();
				for (int m = 0; m < xMove.length; m++) {
					int nextX = x + xMove[m];
					int nextY = y + yMove[m];
					if (isSafe(nextX, nextY)) {
						myQueue.add(nextX,nextY);
					}
				}
				mmm[x][y] = myQueue;
				chessboard[x][y] = -1;
			}
		}

		for (int x = 0; x < chessboard.length; x++) {
			for(int y = 0; y < chessboard[0].length; y++){
				MyList myLists = mmm[x][y];
				myLists.sort();
			}
		}
		start = System.currentTimeMillis();
	}

	/**
	 * 指定的索引是否安全，如果不越界我们就认为是安全的
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isSafe(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}

	/**
	 * 指定索引的元素是否是遍历过, 如果没有遍历过，那么这个索引对应的元素值是 -1
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isEmpty(int x, int y) {
		return chessboard[x][y] == -1;
	}

	long start = 0;
	public void soveKT() {
		soveKT(sourceRow, sourceColumn, 0);
	}

	int count = 0;

	public void print(){
		for (int i = 0; i < chessboard.length; i++) {
			for (int j = 0; j < chessboard[i].length; j++) {
				System.out.print(chessboard[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - start) + "毫秒");
	}

	private void soveKT(int x, int y, int step) {
		chessboard[x][y] = step;
		if (step == N * N - 1) {
			//System.out.println("已经找到"+(++count)+"种解！");
			print();
		}else {
			MyList myQueue = mmm[x][y];
			for (int m = 0;m<myQueue.size;m++) {
				int[] arr = myQueue.get(m);
				if(chessboard[arr[0]][arr[1]] == -1) {
					soveKT(arr[0], arr[1], step + 1);
				}
			}
		}
		chessboard[x][y] = -1;
	}
}
