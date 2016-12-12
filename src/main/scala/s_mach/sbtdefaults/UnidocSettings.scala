package s_mach.sbtdefaults

trait UnidocSettings extends ProjectSettings {
  override def projectSettings =
    super.projectSettings ++
    // Note: this applies unidocSettings to all projects (not just root)
    // but this is ok b/c it unidoc uses a project's aggregates and
    // only root project has aggregates
    UnidocPlugin.unidocSettings
}
