lazy val root = (project in file("."))
  .settings(
    Test / test := (Test / g8Test).toTask("").value
  )
