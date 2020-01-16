@Anno(table = "t_stu")
public class Stu {
    @Anno(main = "t_id")
    private Integer id;
    @Anno(column = "t_name")
    private String name;
    @Anno(column = "t_age")
    private Integer age;

    @Anno(column = "p")
    private Integer p;

    @Anno(column = "v")
    private Integer v;

    @Anno(column = "a")
    private Integer a;

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
