import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

class Vortices{
    int x, y;
    public void setv(int newx, int newy){
        this.x = newx;
        this.y = newy;
    }
    public int caldist(int a, int b, int c){
        return Math.abs(a*this.x + b*this.y + c);
    }
}

class Edges{
    int x1, y1, x2, y2;
    public void setv1(int newx1, int newy1){
        this.x1 = newx1;
        this.y1 = newy1;
    }
    public void setv2(int newx2, int newy2){
        this.x2 = newx2;
        this.y2 = newy2;
    }
}

public class CvShell{
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(stk.nextToken());
        Vortices[] vt = new Vortices[N];
        ArrayList<Edges> eds = new ArrayList<>();
        int tmpx, tmpy;
        Vortices[] tmpvt = new Vortices[N];
        for(int i=0 ; i<N ; i++){
            stk = new StringTokenizer(br.readLine());
            tmpx = Integer.parseInt(stk.nextToken());
            tmpy = Integer.parseInt(stk.nextToken());
            vt[i] = new Vortices();
            vt[i].setv(tmpx, tmpy);
            tmpvt[i] = new Vortices();
            tmpvt[i].setv(0,0);
        }
        heapSort(vt, N, true);
        tmpx = vt[N-1].x;
        int k=0;
        for(int i=N-1 ; i>=0 ; i--){
            if(tmpx != vt[i].x) break;
            tmpvt[N-1-i] = vt[i];
            k++;
        }
        heapSort(tmpvt, k, false);
        Edges tmped = new Edges();
        tmped.setv1(tmpx, tmpvt[k-1].y);
        tmped.setv2(tmpx, tmpvt[0].y);
        eds.add(tmped);
        Edges tmped1 = new Edges();
        tmped1.setv1(tmpx, tmpvt[0].y);
        eds.add(tmped1);
        for(int i=0 ; i<k ; i++){
            tmpvt[i] = new Vortices();
            tmpvt[i].setv(0,0);
        }
        k=0;
        tmpx = vt[0].x;
        for(int i=0; i<N ; i++){
            if(tmpx != vt[i].x) break;
            tmpvt[i] = vt[i];
            k++;
        }
        heapSort(tmpvt, k, false);
        Edges bufed = new Edges();
        bufed.setv1(tmpx, tmpvt[0].y);
        bufed.setv2(tmpx, tmpvt[k-1].y);
        heapSort(vt, N, false);
        tmpy = vt[0].y;
        for(int i=0 ; i<k ; i++){
            tmpvt[i] = new Vortices();
            tmpvt[i].setv(0,0);
        }
        k=0;
        for(int i=0;i<N;i++){
            if(tmpy != vt[i].y) break;
            tmpvt[i] = vt[i];
            k++;
        }
        heapSort(tmpvt, k, true);
        eds.get(1).setv2(tmpvt[k-1].x, tmpy);
        Edges tmped2 = new Edges();
        tmped2.setv1(tmpvt[k-1].x, tmpy);
        tmped2.setv2(tmpvt[0].x, tmpy);
        eds.add(tmped2);
        Edges tmped3 = new Edges();
        tmped3.setv1(tmpvt[0].x, tmpy);
        tmped3.setv2(bufed.x1, bufed.y1);
        eds.add(tmped3);
        eds.add(bufed);
        for(int i=0 ; i<k ; i++){
            tmpvt[i] = new Vortices();
            tmpvt[i].setv(0,0);
        }
        k=0;
        tmpy = vt[N-1].y;
        for(int i=N-1;i>=0;i--){
            if(tmpy != vt[i].y) break;
            tmpvt[N-1-i] = vt[i];
            k++;
        }
        heapSort(tmpvt, k, true);
//        for(Vortices vtmp: tmpvt){
//            System.out.println(vtmp.x +" "+vtmp.y);
//        }
        Edges tmped4 = new Edges();
        tmped4.setv1(bufed.x2, bufed.y2);
        tmped4.setv2(tmpvt[0].x, tmpy);
        eds.add(tmped4);
        Edges tmped5 = new Edges();
        tmped5.setv1(tmpvt[0].x, tmpy);
        tmped5.setv2(tmpvt[k-1].x, tmpy);
        eds.add(tmped5);
        Edges tmped6 = new Edges();
        tmped6.setv1(tmpvt[k-1].x, tmpy);
        tmped6.setv2(eds.get(0).x1, eds.get(0).y1);
        eds.add(tmped6);
        int xmin = eds.get(0).x1;
        int xmax = eds.get(4).x1;
        int ymin = eds.get(6).y1;
        int ymax = eds.get(2).y1;
        int xr = xmin + xmax;
        int yr = ymin + ymax;
        for(int i=0 ; i<eds.size() ; i++){
            Edges tmpe = eds.get(i);
            if(tmpe.x1 == tmpe.x2){
                if(tmpe.y1 == tmpe.y2){
                    eds.remove(i);
                    i--;
                }
            }
        }
        for(int i=0 ; i<eds.size() ; i++){
            Edges tmp = eds.get(i);
            if( (tmp.x1 == xmin) && (tmp.x2 == xmax) ) {
                if (((tmp.y1 == ymin) && (tmp.y2 == ymax)) || ((tmp.y1 == ymax) && (tmp.y2 == ymin))) {
                    for(int j=0 ; j<N ; j++){
                        tmpvt[j] = new Vortices();
                        tmpvt[j].setv(0,0);
                    }
                }
                chkedg(vt, tmpvt, eds, tmp, xr, yr+1, i);
            } else if( ((tmp.x1 == xmax) && (tmp.x2 == xmin)) ) {
                if (((tmp.y1 == ymin) && (tmp.y2 == ymax)) || ((tmp.y1 == ymax) && (tmp.y2 == ymin))) {
                    for(int j=0 ; j<N ; j++){
                        tmpvt[j] = new Vortices();
                        tmpvt[j].setv(0,0);
                    }
                }
                chkedg(vt, tmpvt, eds, tmp, xr, yr+1, i);
            }
            else{
                for(int j=0 ; j<N ; j++){
                    tmpvt[j] = new Vortices();
                    tmpvt[j].setv(0,0);
                }
                chkedg(vt, tmpvt, eds, tmp, xr, yr, i);
            }
        }

        int c = eds.size();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(Integer.toString(c));
        bw.flush();
        bw.close();
    }
    public static void heapifyx(Vortices[] vort, int k, int n) {
        int left = 2 * k + 1;
        int right = left + 1;
        int smaller;
        if (right < n) {
            if (vort[left].x < vort[right].x) smaller = left;
            else smaller = right;
        } else if (left < n) smaller = left;
        else return;
        Vortices tmp = new Vortices();
        if (vort[smaller].x < vort[k].x) {
            tmp = vort[k];
            vort[k] = vort[smaller];
            vort[smaller] = tmp;
            heapifyx(vort, smaller, n);
        }
    }
    public static void heapifyy(Vortices[] vort, int k, int n){
        int left = 2*k+1;
        int right = left + 1;
        int smaller;
        if(right < n){
            if(vort[left].y < vort[right].y) smaller = left;
            else smaller = right;
        }
        else if (left < n) smaller = left;
        else return;
        Vortices tmp = new Vortices();
        if(vort[smaller].y < vort[k].y){
            tmp = vort[k];
            vort[k] = vort[smaller];
            vort[smaller] = tmp;
            heapifyy(vort, smaller, n);
        }
    }
    public static void heapSort(Vortices[] vort, int n, boolean isx){
        if(isx){
            for(int i=n/2 -1 ; i>=0 ; i--){
                heapifyx(vort, i, n);
            }
            for(int i=n-1 ; i>0 ; i--){
                Vortices tmp = new Vortices();
                tmp = vort[i];
                vort[i] = vort[0];
                vort[0] = tmp;
                heapifyx(vort, 0, i);
            }
        }
        else{
            for(int i=n/2 -1 ; i>=0 ; i--){
                heapifyy(vort, i, n);
            }
            for(int i=n-1 ; i>0 ; i--){
                Vortices tmp = new Vortices();
                tmp = vort[i];
                vort[i] = vort[0];
                vort[0] = tmp;
                heapifyy(vort, 0, i);
            }
        }
    }
    public static void chkedg(Vortices[] vort, Vortices[] tmpvort, ArrayList<Edges> eds, Edges ed, int xr, int yr, int ind){
        int a = ed.y1 - ed.y2;
        int b = ed.x2 - ed.x1;
        int c = ed.y2 * ed.x1 - ed.x2 * ed.y1;
        int N = vort.length;
        int i=0;
        int ii=0;
        boolean checkin = false;
        for(int k=0;k<N ; k++){
            Vortices vt = vort[k];
            if( (a*vt.x + b*vt.y + c) * (a*xr + b*yr + 2*c) < 0){
                checkin = true;
                tmpvort[i].setv(k, vt.caldist(a, b, c));
                i++;
            }
        }
        if(checkin) {
            heapSort(tmpvort, i, false);
            int maxd = tmpvort[0].y;
            for(int j=0 ; j<i ; j++){
                if(tmpvort[j].y != maxd) break;
                tmpvort[j].setv(tmpvort[j].x, Math.abs(vort[tmpvort[j].x].x - ed.x1));
                ii++;
            }
            heapSort(tmpvort, ii, false);
            Edges tmped1 = eds.get(tmpvort[ii-1].x);
            eds.remove(tmpvort[ii-1].x);
            Edges tmped2 = new Edges();
            tmped2.setv1(tmped1.x1, tmped1.y1);
            tmped2.setv2(vort[tmpvort[ii-1].x].x,vort[tmpvort[ii-1].x].y);
            eds.add(tmpvort[ii-1].x, tmped2);
            Edges tmped3 = new Edges();
            tmped3.setv1(tmped2.x1, tmped2.y1);
            tmped3.setv2(tmped1.x2, tmped1.y2);
            eds.add(tmpvort[ii-1].x+1, tmped3);
        }
    }
}