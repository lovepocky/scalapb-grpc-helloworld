import java.io.{ByteArrayInputStream, InputStream}

import kv.kv.{GetRequest, SetRequest}

/**
  * Created by lovepocky on 17/3/14.
  */
object KVProtobufClientOverHttp {

    import scalaj.http._
    val host = "localhost"
    val port = 9006

    /**
      * client test of [gRPC bridge](https://lyft.github.io/envoy/docs/install/sandboxes.html#grpc-bridge)
      */

    def main(args: Array[String]): Unit = {

        //[Host headers can't be set #64](https://github.com/scalaj/scalaj-http/issues/64)
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true")

        //set()
        get()
    }

    def set() = {
        val request = SetRequest("hello", "world")
        val response = Http(s"http://$host:$port/kv.KV/Set")
            .headers(
                "content-type" -> "application/grpc"
                , "Host" -> "grpc"
            ).postData(serialize(request)).asString
        println(response)
    }

    def get() = {
        val request = GetRequest("hello")
        val response = Http(s"http://$host:$port/kv.KV/Get")
            .headers(
                "content-type" -> "application/grpc"
                , "Host" -> "grpc"
            ).postData(serialize(request)).asBytes
        println(response)
        println(response.body.toSeq)
        //probably incorrect here (deserialize)
        val res = scala.io.Source.fromBytes(response.body.drop(5)).mkString
        println(res)
    }

    def serialize[T <: com.trueaccord.scalapb.GeneratedMessage](a: T): Array[Byte] = {
        import java.io.{ByteArrayOutputStream, ObjectOutputStream}
        import java.nio.ByteBuffer

        val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
        stream.write(0)
        stream.write(ByteBuffer.allocate(4).putInt(a.serializedSize).array())
        a.writeTo(stream)
        stream.toByteArray
    }

}
