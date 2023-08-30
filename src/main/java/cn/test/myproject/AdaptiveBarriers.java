package cn.test.myproject;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Data
@AllArgsConstructor
class Entourage{
    private int[] figure;
    private boolean holyShield;
}
public class AdaptiveBarriers {

    public static void main(String[] args) {

    }

    boolean hasHolyShield;
    int [] self = new int[] {9,1};

    public int[] attacked(List<Entourage> attacks) {
        for (Entourage e : attacks) {
            if (hasHolyShield){
                hasHolyShield = false;
                self = new int[] {self[0] + self[1] - 1,1};
                System.out.println("被 " + Arrays.toString(e.getFigure()) + " 敌人攻击变形为 " + Arrays.toString(self) + "圣盾被破，剩余身材： " + Arrays.toString(self) +
                        (e.isHolyShield() || self[0] < e.getFigure()[1] ? " 敌人还活着身材为 //TODO" : " 敌人被杀结果为" + Arrays.toString(new int[] {e.getFigure()[0],e.getFigure()[1] - self[0]})));
            }
            return null;
        }
        return null;
    }

    public int[] gained(int [] gain, boolean hasHolyShield) {
        this.hasHolyShield = hasHolyShield;
        self = IntStream.range(0, 2)
                .mapToObj(i -> self[i] + gain[i])
                .mapToInt(Integer::intValue)
                .toArray();
        return self;
    }

    @Test
    public void testAttacked() {
        System.out.println(Arrays.toString(gained(new int [] {1,100},true)));
        System.out.println(Arrays.toString(attacked(Lists.newArrayList(new Entourage(new int[]{55,50},true)))));
    }
    @Test
    public void testGained() {
        System.out.println(Arrays.toString(gained(new int [] {0,0},true)));
    }
}
