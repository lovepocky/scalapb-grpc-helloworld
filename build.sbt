name := "ScalaPB-gRPC-HelloWorld"

version := "1.0"

scalaVersion := "2.11.8"

PB.targets in Compile := Seq(
    scalapb.gen() -> (sourceManaged in Compile).value
)

// If you need scalapb/scalapb.proto or anything from
// google/protobuf/*.proto
libraryDependencies ++= Seq(
    //ScalaPB
    "com.trueaccord.scalapb" %% "scalapb-runtime" % com.trueaccord.scalapb.compiler.Version.scalapbVersion % "protobuf"
    , "io.grpc" % "grpc-netty" % "1.1.2"
    , "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion

    //, "com.google.protobuf" % "protobuf-java-util" % "3.2.0"
    //, "com.google.protobuf" % "protoc" % "3.2.0"

    //utils
    , "org.scalaj" %% "scalaj-http" % "2.3.0"
)
