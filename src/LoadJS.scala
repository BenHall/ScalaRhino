import org.mozilla.javascript.{Context}
import sun.org.mozilla.javascript.internal.Scriptable


object LoadJS {
  def main(args: Array[String]) {
    val cx = Context.enter();
    try {
            // Initialize the standard objects (Object, Function, etc.)
            // This must be done before scripts can be executed. Returns
            // a scope object that we use in later calls.
            val scope = cx.initStandardObjects();

            val s = createSwingFrame(args);

            cx.evaluateString(scope, s, "<cmd>", 1, null);

        } finally {
            Context.exit();
        }
  }

  def createSwingFrame(additionalCodeToExecute: Array[String]): String =
  {
    var s = "var swingNames = JavaImporter();"
    s += " var swingNames = JavaImporter();"
    s += " swingNames.importPackage(Packages.javax.swing);"
    s += " swingNames.importPackage(Packages.java.awt);"
    s += " swingNames.importPackage(Packages.java.awt.event);"
    s += " try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (e) {}"
    s += " var frame = new swingNames.JFrame(\"SwingApplication\");"

    for (i <- additionalCodeToExecute) {
        s += i;
    }

    s += " frame.pack();"
    s += " frame.setVisible(true);"
    return s;
  }
}