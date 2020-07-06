package generic;
class A{
    public void run(){
        System.out.printf("A");
    }
}
class B extends A{
    public void run(){
        System.out.printf("B");
    }
}
class C extends B{
    public void run(){
        System.out.printf("C");
    }
}
class D extends C{
    public void run(){
        System.out.printf("D");
    }
}
class E{
    public void run(){
        System.out.printf("E");
    }
}
class TTT<T>{
    T o ;
    TTT(T o){
        this.o  = o;
    }

}
public class GenericTest {
    public static TTT test(TTT<? super C> o){
        return o;
    }
    public static void main(String[] args) {

      TTT<? super C> x = new TTT< >(new D());
      TTT<? super C> x1 = new TTT<C>(new C());
      TTT<? super C> x2 = new TTT<B>(new B());
      TTT<? super C> x3 = new TTT<A>(new A());
      TTT<? super C> x4 = new TTT< >(new E());


    }
}
