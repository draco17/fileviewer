application {
    title='FileViewer'
    startupGroups = ['FileViewer']

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "FileViewer"
    FileViewer {
        model = 'FileViewerModel'
        view = 'FileViewerView'
        controller = 'FileViewerController'
    }

}
