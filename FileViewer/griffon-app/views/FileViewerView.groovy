import static java.awt.Color.BLACK
import static griffon.util.GriffonApplicationUtils.*

def blackShadowBorder = {
   compoundBorder(
      inner: emptyBorder(3),
      outer: compoundBorder(
         inner: lineBorder(color: BLACK),
         outer: dropShadowBorder()
      )
   )
}

actions {
   action( id: 'exitAction',
      name: 'Quit',
      closure: controller.exit,
      mnemonic: 'Q',
      accelerator: shortcut('Q'),
   )

   action( id: 'aboutAction',
      name: 'About',
      closure: controller.about,
      mnemonic: 'B',
      accelerator: shortcut('B')
   )

   action( id: 'viewAction',
      name: 'View',
      enabled: bind { model.fileUrl && model.fileUrl.size() > 0 },
      closure: controller.viewFile,
      mnemonic: 'V',
      accelerator: shortcut('V'),
   )
}

application( title: "FileViewer", size: [480,320], locationByPlatform: true,
             iconImage: imageIcon("/groovy/ui/ConsoleIcon.png").image ) {
   menuBar( id: 'menuBar') {
      menu(text: 'File', mnemonic: 'F') {
         menuItem(viewAction)
         if( !isMacOSX ) {
            separator()
            menuItem(exitAction)
         }
      }

      if( !isMacOSX ) {
         glue()
         menu(text: 'Help', mnemonic: 'H') {
            menuItem(aboutAction)
         }
      }
   }
   borderLayout()
   jxtitledPanel(title: "File Location", border: blackShadowBorder(),
                 constraints: context.NORTH) {
      panel {
      borderLayout()
         textField(columns: 120, constraints: context.CENTER, id: "urltf")
         button(viewAction, constraints: context.EAST)
         bean(model, fileUrl: bind{ urltf.text })
      }
   }
   jxtitledPanel(title: "Contents", border: blackShadowBorder(),
                 constraints: context.CENTER) {
      scrollPane {
         editorPane( id: "fileview", editable: false,
           text: bind { model.fileContents } )
      }
   }
}