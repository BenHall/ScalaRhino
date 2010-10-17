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

            var labelContents = "Called at : " + (new java.util.Date()).getTime();
            for (arg <- args) {
                labelContents += arg;
            }

            var additionalCodeSnippets = List(" var label = new swingNames.JLabel(\"%s\");".format(labelContents),
                                  " var pane = new swingNames.JPanel(); pane.setLayout(new swingNames.GridLayout(0, 1));",
                                  " pane.add(label);",
                                  " frame.getContentPane().add(pane, swingNames.BorderLayout.CENTER);")

            

            val s = createSwingFrame(additionalCodeSnippets);

            cx.evaluateString(scope, s, "<cmd>", 1, null);

        } finally {
            Context.exit();
        }
  }

  def createSwingFrame(additionalCodeToExecute: List[String]): String =
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