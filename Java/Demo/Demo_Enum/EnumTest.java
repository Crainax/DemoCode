public class EnumTest {

    public static void main(String[] args) {
        ColorEnum green = ColorEnum.green;
        System.out.println(green);
        //打印结果:Green

        SeasonEnum season = SeasonEnum.getSeason(10);
        System.out.println("season = " + season);
        //打印结果:winter

        PersonEnum zhuXi = PersonEnum.zhuXi;
        System.out.println("zhuXi = " + zhuXi);
        //打印结果:zhuXi,但是可以通过重写toString方法后会打印这个结果:PersonEnum{age=50, name='xijinping'}
    }

    //与类不同的是,可以写成public类型的enum内部形式.
    public enum ColorEnum {

        white, red, green, blue;

    }


    public enum SeasonEnum {
        spring, summer, autumn, winter;

        public final static String position = "test";

        /**
         * 枚举中有一个自带的静态方法values(),返回enum实例的数据并且该数组中的元素顺序和声明时的顺序一样
         * 枚举也可以像普通的类一样可以添加属性和方法，可以为它添加静态和非静态的属性或方法
         */
        //枚举类下的静态方法,能根据月份获取对应的枚举类型,与类十分相似.
        public static SeasonEnum getSeason(int month) {
            if (month < 4 && month > 0)
                return spring;
            else if (month < 7)
                return summer;
            else if (month < 10)
                return autumn;
            else if (month < 13)
                return winter;

            return null;

        }


    }

    public enum PersonEnum  {
        zhuXi(50,"xijinping"),zongli(51,"likeqiang");

        private final int age;
        private final String name;

        //写成public 形式的构造器会报错.
        PersonEnum(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "PersonEnum{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}