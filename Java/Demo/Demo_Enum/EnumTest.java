public class EnumTest {

    public static void main(String[] args) {
        ColorEnum green = ColorEnum.green;
        System.out.println(green);
        //��ӡ���:Green

        SeasonEnum season = SeasonEnum.getSeason(10);
        System.out.println("season = " + season);
        //��ӡ���:winter

        PersonEnum zhuXi = PersonEnum.zhuXi;
        System.out.println("zhuXi = " + zhuXi);
        //��ӡ���:zhuXi,���ǿ���ͨ����дtoString��������ӡ������:PersonEnum{age=50, name='xijinping'}
    }

    //���಻ͬ����,����д��public���͵�enum�ڲ���ʽ.
    public enum ColorEnum {

        white, red, green, blue;

    }


    public enum SeasonEnum {
        spring, summer, autumn, winter;

        public final static String position = "test";

        /**
         * ö������һ���Դ��ľ�̬����values(),����enumʵ�������ݲ��Ҹ������е�Ԫ��˳�������ʱ��˳��һ��
         * ö��Ҳ��������ͨ����һ������������Ժͷ���������Ϊ����Ӿ�̬�ͷǾ�̬�����Ի򷽷�
         */
        //ö�����µľ�̬����,�ܸ����·ݻ�ȡ��Ӧ��ö������,����ʮ������.
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

        //д��public ��ʽ�Ĺ������ᱨ��.
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