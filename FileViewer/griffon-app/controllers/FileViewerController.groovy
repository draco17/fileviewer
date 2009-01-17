import javax.swing.JOptionPane

class FileViewerController {
   def model
   def view

   def exit = { evt = null ->
      app.shutdown()
   }

   def about = { evt = null ->
      def pane = view.optionPane()
      pane.setMessage("Welcome to FileViewer,\na sample Griffon application.")
      def dialog = pane.createDialog(app.appFrames[0], 'About FileViewer')
      dialog.show()
   }

   def viewFile = { evt = null ->
      model.fileContents = ""
      def file = new File(model.fileUrl)
      if( !file.exists() ) {
         error("FileViewer - Error", "${file.absolutePath}\ndoesn't exist.")
         return
      }
      if( file.isDirectory() ) {
         error("FileViewer - Error", "${file.absolutePath}\nis not a plain file.")
         return
      }
      if( !file.canRead() ) {
         error("FileViewer - Error", "${file.absolutePath}\ncan not be read.")
         return
      }

      doOutside {
         // read the whole file contents in a background thread
         def text = file.text
         doLater { model.fileContents = text }
      }
   }

   def displayDialog( type, title, message ) {
      doLater {
         JOptionPane.showMessageDialog(app.appFrames[0], message.toString(),
               title, type )
      }
   }
   def info = this.&displayDialog.curry(JOptionPane.INFORMATION_MESSAGE)
   def warning = this.&displayDialog.curry(JOptionPane.WARNING_MESSAGE)
   def error = this.&displayDialog.curry(JOptionPane.ERROR_MESSAGE)
}