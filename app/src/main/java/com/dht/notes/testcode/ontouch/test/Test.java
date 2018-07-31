package com.dht.notes.testcode.ontouch.test;

import android.util.Log;

/**
 * Created by dai on 2018/5/24.
 */

public class Test {
    /**
     * 打印蛇形矩阵NxN
     *
     * @param n 列数
     */
    public void printNxN(int n) {
        int[][] array = new int[n][n];
        int t = 0, i = 0, j = 0;
        for (int k = 0; k < n / 2; k++) {

        }

    }

    /**
     * for(int k=0;k<n/2;k++){//k代表第几圈
     * for(i=k,j=k;j<n-k-1;j++){//第一条边
     * A[i][j]=t++;
     * }
     * for(i=k,j=n-k-1;i<n-k-1;i++){//第二条边
     * A[i][j]=t++;
     * }
     * for(i=n-k-1,j=n-k-1;j>k;j--){//第三条边
     * A[i][j]=t++;
     * }
     * for(i=n-k-1,j=k;i>k;i--){//第四条边
     * A[i][j]=t++;
     * }
     * <p>
     * <p>
     * }
     * if(n%2==1){
     * A[n/2][n/2]=t;
     * }
     * <p>
     * for(i=0;i<n;i++){
     * for (j=0;j<n;j++) {
     * System.out.print(A[i][j]+" ");
     * }
     * System.out.println();
     * }
     */
    private static final String TAG = "Test";

    public void test() {
        Log.d(TAG, "test: Java 单元测试 ");
    }

}
