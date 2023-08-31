package cn.test.myproject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Data
@AllArgsConstructor
class Entourage{
    private int[] figure;
    private boolean holyShield;

    public Entourage(int[] figure) {
        this.figure = figure;
        this.holyShield = false;
    }

    public String info(){
        return Arrays.toString(this.figure) + (this.figure[1] < 0 ? " (已阵亡) " : (holyShield ? " (有圣盾) " : " (无圣盾) "));
    }

    public String info(boolean holyShield){
        return Arrays.toString(this.figure) + (this.figure[1] < 0 ? " (已阵亡) " : (holyShield ? " (有圣盾) " : " (无圣盾) "));
    }
}

@AllArgsConstructor
class gainedThread extends Thread{
    @SneakyThrows
    @Override
    public void run() {
        AdaptiveBarriers adaptiveBarriers = new AdaptiveBarriers();
        Random random = new Random();
        while (true){
            adaptiveBarriers.gained(new int[] {random.nextInt(101),random.nextInt(101)},random.nextBoolean());
            Thread.sleep(3000);
        }
    }
}

@AllArgsConstructor
class AttackedThread extends Thread{
    @SneakyThrows
    @Override
    public void run() {
        AdaptiveBarriers adaptiveBarriers = new AdaptiveBarriers();
        Random random = new Random();
        while (true){
            adaptiveBarriers.attacked(new Entourage(new int[] {random.nextInt(101),random.nextInt(101)},random.nextBoolean()));
            Thread.sleep(1000);
        }
    }
}

public class AdaptiveBarriers {

    public static void main(String[] args) throws InterruptedException {
        gainedThread gainedThread = new gainedThread();
        AttackedThread attackedThread = new AttackedThread();
        gainedThread.setDaemon(true);
        gainedThread.start();
        Thread.sleep(100);
        attackedThread.start();
    }

    static Entourage self = new Entourage(new int[] {9,1},false);

    @SneakyThrows
    public void attacked(Entourage e) {
            System.out.print("当前身材 " + self.info());
            if (self.isHolyShield()){
                self.setHolyShield(false);
                self.setFigure(new int[] {self.getFigure()[0] + self.getFigure()[1] - 1,1});
                System.out.println(" 被 " + e.info() + " 敌人攻击变形为 " + self.info() + " 圣盾被破，剩余身材： " + self.info() +
                        (e.isHolyShield() || self.getFigure()[0] < e.getFigure()[1] ? " 敌人还活着身材为 :" + (e.isHolyShield() ? e.info(false) :
                                new Entourage(new int[]{e.getFigure()[0],e.getFigure()[1]}).info()) :  " 敌人被杀结果为" + new Entourage(new int[] {e.getFigure()[0],e.getFigure()[1] - self.getFigure()[0]}).info())
                );
            } else {
                if (self.getFigure()[0] + self.getFigure()[1] - 1 < e.getFigure()[0]) {
                    self.setFigure(new int[] {self.getFigure()[0] + self.getFigure()[1] - 1,1});
                    System.out.println("受到敌人" + e.info() + "致命攻击，我已经无法再继续保护你了，我将倾尽全力给予敌人致命一击！变形为 " + self.info() + " 剩余身材： " +
                            new Entourage(new int[]{self.getFigure()[0],1 - e.getFigure()[0]}).info() + (e.isHolyShield() || self.getFigure()[0] < e.getFigure()[1] ? " 敌人还活着身材为 :" + (e.isHolyShield() ? e.info(false) :
                                    new Entourage(new int[]{e.getFigure()[0],e.getFigure()[1] - self.getFigure()[0]}).info()) :  " 敌人被杀结果为 " + new Entourage(new int[] {e.getFigure()[0],e.getFigure()[1] - self.getFigure()[0]}).info()));
                    System.exit(0);
                } else {
                    self.setFigure(new int[] {self.getFigure()[0] + self.getFigure()[1] - e.getFigure()[0] - 1,e.getFigure()[0] + 1});
                    System.out.println(" 被 " + e.info() + " 敌人攻击变形为 " + self.info() + " 剩余身材： " + new Entourage(new int[]{self.getFigure()[0],self.getFigure()[1] - e.getFigure()[0]}).info() +
                            (e.isHolyShield() || self.getFigure()[0] < e.getFigure()[1] ? " 敌人还活着身材为 :" + (e.isHolyShield() ? e.info(false) :
                                    new Entourage(new int[]{e.getFigure()[0],e.getFigure()[1]}).info()) :  " 敌人被杀结果为 " + new Entourage(new int[] {e.getFigure()[0],e.getFigure()[1] - self.getFigure()[0]}).info()));
                    self.setFigure(new int[]{self.getFigure()[0] + self.getFigure()[1] - e.getFigure()[0] - 1,1});
                }

            }
    }

    public void gained(int [] gain, boolean hasHolyShield) {
        self.setHolyShield(self.isHolyShield() || hasHolyShield);
        int [] figure;
        figure = IntStream.range(0, 2)
                .map(i -> self.getFigure()[i] + gain[i])
                .toArray();
        self.setFigure(figure);
        System.out.println("受到增益！身材增加 " + new Entourage(gain,hasHolyShield).info() + " 当前身材 " + self.info());
    }

    @Test
    public void testAttacked1() {
        gained(new int [] {64,15},false);
        attacked(new Entourage(new int[]{73,76},true));
    }

    @Test
    public void testAttacked2() {
        gained(new int [] {1,20},false);
        attacked(new Entourage(new int[]{5,6},false));
    }
    @Test
    public void testAttacked3() {
        gained(new int [] {1,20},true);
        attacked(new Entourage(new int[]{999,20},false));
    }
    @Test
    public void testAttacked4() {
        gained(new int [] {1,20},true);
        attacked(new Entourage(new int[]{55,50},true));
    }
    @Test
    public void testGained() {
        gained(new int [] {5,6},true);
    }
}
