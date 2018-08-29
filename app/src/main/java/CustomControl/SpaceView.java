package CustomControl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.example.nnaija.sensormisession.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import module.Astre;

public class SpaceView extends View {


    private List<Astre> listeAstre;
    private List<AstreView> listeAstreView;

    private Bitmap vaiseau;

    private int vaisseauPostionX;
    private  int vaisseauPositionY;
    private  Rect VaisseauRect;

    private Point vaisseauSize;
    private Paint paint;
    private Paint paintAstre;
    private Paint paintText;

    private  int deviceWidth;
    private int deviceHeight;

    private AstreView astreViewClose;
    private AstreView astreViewPreviousClose;
    private AstreView astreViewDummy;

    private Context context;


    public SpaceView(Context context,AttributeSet attrs,List<Astre> listeAstre) {
        super(context,attrs);

//        listeAstre=new ArrayList<>();
        deviceWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
        deviceHeight=(Resources.getSystem().getDisplayMetrics().heightPixels)-getActionBarSize()-getStatusBarHeight();

        this.context=context;

        listeAstreView=new ArrayList<>();
        Random random =new Random(System.currentTimeMillis()%1000);
        Point position;


        for(int cnt=0;cnt<listeAstre.size();cnt++){
            position=new Point(random.nextInt(deviceWidth),random.nextInt(deviceHeight));
            listeAstreView.add(new AstreView(context,null,listeAstre.get(cnt),position));
        }


         astreViewDummy=new AstreView(context);
        init();
    }


    private void init() {


        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        paintAstre=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintAstre.setColor(Color.WHITE);
        paintAstre.setStyle(Paint.Style.FILL);
        paintAstre.setTextSize(50);

        paintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(50);



        vaiseau= BitmapFactory.decodeResource(getResources(),R.drawable.vaisseau);
        vaisseauSize=new Point(100,100);
        VaisseauRect=new Rect(getVaisseauPostionX(),vaisseauPositionY, getVaisseauPostionX() +vaisseauSize.x,vaisseauPositionY+vaisseauSize.y);







    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawRect(0,0,deviceWidth,deviceHeight,paint);
        drawAstres(canvas);
        canvas.drawText("x="+String.valueOf(getVaisseauPostionX()),100,100,paintText);
        canvas.drawText("y="+String.valueOf(getVaisseauPositionY()),100,200,paintText);


        VaisseauRect.set(getVaisseauPostionX(),vaisseauPositionY, getVaisseauPostionX() +vaisseauSize.x,vaisseauPositionY+vaisseauSize.y);
        canvas.drawBitmap(vaiseau,null,VaisseauRect,null);


        analysePosition();
        invalidate();


    }


    private  void drawAstres(Canvas canvas){
        for(int cnt=0;cnt<listeAstreView.size();cnt++){
           listeAstreView.get(cnt).draw(canvas);
        }
    }
    private   int getActionBarSize(){
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public int getVaisseauPostionX() {
        return vaisseauPostionX;
    }

    public void setVaisseauPostionX(int vaisseauPostionX) {
        this.vaisseauPostionX = vaisseauPostionX;
    }
    public int getVaisseauPositionY() {
        return vaisseauPositionY;
    }
    public void setVaisseauPostionY(int vaisseauPostionY) {
        this.vaisseauPositionY = vaisseauPostionY;
    }
    private void analysePosition(){
        astreViewClose=astreViewDummy;

        for(int cnt=0;cnt<listeAstreView.size();cnt++){
            final int vaisseauPostionX = getVaisseauPostionX();
            final int vaisseauPostionY=getVaisseauPositionY();
            if(vaisseauPostionX >=listeAstreView.get(cnt).position.x && vaisseauPostionX<=listeAstreView.get(cnt).rightEdge){
                if(vaisseauPostionY>=listeAstreView.get(cnt).position.y && vaisseauPostionY<=listeAstreView.get(cnt).bottomEdge){
                    astreViewClose=nearestAstre(astreViewClose,listeAstreView.get(cnt));
                }
            }

        }
            if(astreViewClose.position.x!=-1&&astreViewPreviousClose!=astreViewClose){
                displayInfo(astreViewClose);
                astreViewPreviousClose=astreViewClose;
            }

    }

    private void displayInfo(AstreView astreView) {
        String info="Nom: "+astreView.astre.getNom()+"\n"+
                "Taille: "+astreView.astre.getTaille()+"\n"+
                "Status: "+(astreView.astre.isStatus()?"Habitable":"non habitable");

        if(astreView.astre.isStatus()){
            astreView.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        }
        Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
    }




    private AstreView nearestAstre(AstreView astre1,AstreView astre2){

        if(astre1.position.x==-1){
         return astre2;
        }
        if(astre2.position.x==-1){
            return astre1;
        }

        final int vaisseauPostionX = getVaisseauPostionX();
        final int vaisseauPostionY=getVaisseauPositionY();

        double distanceVaisseauAstre1=Math.sqrt(Math.pow(astre1.position.x-vaisseauPostionX,2)+Math.pow(astre1.position.y-vaisseauPostionY,2));
        double distanceVaisseauAstre2=Math.sqrt(Math.pow(astre2.position.x-vaisseauPostionX,2)+Math.pow(astre2.position.y-vaisseauPostionY,2));

        return (distanceVaisseauAstre1<=distanceVaisseauAstre2)?astre1:astre2;


    }

    public class AstreView extends View {

        private Astre astre;
        private Paint paintAstre;
        private Point position;
        private Drawable drawable;
        private  int rightEdge;
        private int bottomEdge;

        private   Random random;




        public AstreView(Context context, @Nullable AttributeSet attrs, Astre astre,Point position) {
            super(context, attrs);
            this.astre=astre;
            this.position=position;

            init();
        }

        public AstreView(Context context){
            super(context);
            this.position=new Point();
            this.position.x=-1;
            this.position.y=-1;

        }

        private void init() {

            paintAstre=new Paint(Paint.ANTI_ALIAS_FLAG);
            paintAstre.setColor(Color.WHITE);
            paintAstre.setStyle(Paint.Style.FILL);
            paintAstre.setTextSize(50);
            rightEdge=position.x+astre.getTaille()*50;
            bottomEdge=((astre.getTaille()*50)+position.y);
            drawable=astre.getImage();
        }


//        public void drawAstre(Canvas canvas){
//            drawable.setBounds(astrePosition.x,astrePosition.y,astrePosition.x+astre.getTaille()*50,(astre.getTaille()*50)+astrePosition.y);
//            drawable.draw(canvas);
//        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            drawable.setBounds(position.x,position.y,rightEdge,bottomEdge);
            drawable.draw(canvas);
        }

        public void setColorFilter(int color,PorterDuff.Mode mode){

            drawable.setColorFilter(color,mode);
        }

    }


}
