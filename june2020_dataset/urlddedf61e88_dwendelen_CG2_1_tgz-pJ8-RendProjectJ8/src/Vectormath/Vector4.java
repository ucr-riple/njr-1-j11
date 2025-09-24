
package Vectormath;

public class Vector4 extends Object {

  public double[] data =new double[4];

  public Vector4() {
    data[0] = 0.0;
    data[1] = 0.0;
    data[2] = 0.0;
    data[3] = 1.0;
  }

  public Vector4(Vector3 v) {
    data[0] = v.data[0];
    data[1] = v.data[1];
    data[2] = v.data[2];
    data[3] = 1.0;
  }

   public Vector4(Vector3 v, double h) {
    data[0] = v.data[0];
    data[1] = v.data[1];
    data[2] = v.data[2];
    data[3] = h;
  }
  
  public Vector4(double a, double b, double c,double d) {
    data[0] = a;
    data[1] = b;
    data[2] = c;
    data[3] = d;
  }

  public Vector3 toVector3() {
    Vector3 tmp = new Vector3();
    tmp.data[0] = this.data[0]/this.data[3];
    tmp.data[1] = this.data[1]/this.data[3];
    tmp.data[2] = this.data[2]/this.data[3];
    return(tmp);
  }

  public static Vector4 add(Vector4 a,Vector4 b) {
    Vector4 tmp = new Vector4();
    tmp.data[0] = a.data[0] + b.data[0];
    tmp.data[1] = a.data[1] + b.data[1];
    tmp.data[2] = a.data[2] + b.data[2];
    tmp.data[3] = a.data[3] + b.data[3];
    return(tmp);
  }

  public static Vector4 add(Vector4 a,Vector4 b,Vector4 c) {
    Vector4 tmp = new Vector4();
    tmp.data[0] = a.data[0] + b.data[0] + c.data[0];
    tmp.data[1] = a.data[1] + b.data[1] + c.data[1];
    tmp.data[2] = a.data[2] + b.data[2] + c.data[2];
    tmp.data[3] = a.data[3] + b.data[3] + c.data[3];
    return(tmp);
  }

  public static Vector4 subtract(Vector4 a,Vector4 b) {
    Vector4 tmp = new Vector4();
    tmp.data[0] = a.data[0] - b.data[0];
    tmp.data[1] = a.data[1] - b.data[1];
    tmp.data[2] = a.data[2] - b.data[2];
    tmp.data[3] = a.data[3] - b.data[3];
    return(tmp);
  }

  public Vector4 scale(double s) {
    Vector4 tmp = new Vector4();
    tmp.data[0] = this.data[0] * s;
    tmp.data[1] = this.data[1] * s;
    tmp.data[2] = this.data[2] * s;
    tmp.data[3] = this.data[3] * s;
    return(tmp);
  }

  public void printlnVector4() {
    System.out.println("( "+this.data[0]+" , "+this.data[1]+" , "+this.data[2]+" , "+this.data[3]+" )");
  }

  public Vector4 makeCopy() {
    Vector4 tmp = new Vector4(this.data[0],this.data[1],this.data[2],this.data[3]);
    return(tmp);
  }

  public static Vector4 linearCombination(double a,Vector4 x,double b,Vector4 y) {
    Vector4 result = new Vector4();
    result.data[0] = a*x.data[0] + b*y.data[0];
    result.data[1] = a*x.data[1] + b*y.data[1];
    result.data[2] = a*x.data[2] + b*y.data[2];
    result.data[3] = a*x.data[3] + b*y.data[3];
    return(result);
  }

  public static Vector4 linearCombination(double a,Vector4 x,double b,Vector4 y,double c,Vector4 z) {
    Vector4 result = new Vector4();
    result.data[0] = a*x.data[0] + b*y.data[0] + c*z.data[0];
    result.data[1] = a*x.data[1] + b*y.data[1] + c*z.data[1];
    result.data[2] = a*x.data[2] + b*y.data[2] + c*z.data[2];
    result.data[3] = a*x.data[3] + b*y.data[3] + c*z.data[3];
    return(result);
  }

  public static Vector4 linearCombination(Vector4 x,double b,Vector4 y,double c,Vector4 z) {
    Vector4 result = new Vector4();
    result.data[0] = x.data[0] + b*y.data[0] + c*z.data[0];
    result.data[1] = x.data[1] + b*y.data[1] + c*z.data[1];
    result.data[2] = x.data[2] + b*y.data[2] + c*z.data[2];
    result.data[3] = x.data[3] + b*y.data[3] + c*z.data[3];
    return(result);
  }

  public static Vector4 linearCombination(Vector4 x,double b,Vector4 y) {
    Vector4 result = new Vector4();
    result.data[0] = x.data[0] + b*y.data[0];
    result.data[1] = x.data[1] + b*y.data[1];
    result.data[2] = x.data[2] + b*y.data[2];
    result.data[3] = x.data[3] + b*y.data[3];
    return(result);
  }

  public static Vector4 linearCombination(double a,Vector4 x,Vector4 y) {
    Vector4 result = new Vector4();
    result.data[0] = a*x.data[0] + y.data[0];
    result.data[1] = a*x.data[1] + y.data[1];
    result.data[2] = a*x.data[2] + y.data[2];
    result.data[3] = a*x.data[3] + y.data[3];
    return(result);
  }
}