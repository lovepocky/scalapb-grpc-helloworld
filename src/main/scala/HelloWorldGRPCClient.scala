import io.grpc.internal.{AbstractManagedChannelImplBuilder, DnsNameResolverProvider}

/**
  * Created by lovepocky on 17/3/12.
  */
object HelloWorldGRPCClient {

    def main(args: Array[String]): Unit = {
        import scala.concurrent.Future
        //import com.example.protos.hello._
        import io.grpc.examples.helloworld.helloworld._

        import io.grpc._
        import io.grpc.netty.NettyChannelBuilder
        import io.grpc.{StatusRuntimeException, ManagedChannelBuilder, ManagedChannel}

        //Creating a channel:
        val host: String = "localhost"
        val port: Int = 50051
        //val channel = NettyChannelBuilder.forAddress(host, port).usePlaintext(true).build
        val channel = ManagedChannelBuilder.forAddress(host, port)
            .asInstanceOf[ManagedChannelBuilder[_ <: ManagedChannelBuilder[_]]]
            .usePlaintext(true).build
        val request = HelloRequest(name = "World")

        //Blocking call:
        val blockingStub = GreeterGrpc.blockingStub(channel)
        val reply: HelloReply = blockingStub.sayHello(request)
        println(reply)

        //Async call:
        val asyncStub = GreeterGrpc.stub(channel)
        val f: Future[HelloReply] = asyncStub.sayHello(request)
        import scala.concurrent.ExecutionContext.Implicits.global
        f.onSuccess {
            case reply: HelloReply => println(reply)
        }
    }

    def bug: Unit = {

        /**
          * A: ManagedChannelBuilder
          */

        class A[T <: A[T]] {
            def retAmistake: A[_] = ???

            def retA: A[_ <: A[_]] = ???

            def retT: T = ???
        }

        class B extends A[B]

        (new B).retAmistake.retT
        (new B).retA.retT.retA.retT.retA.retT
        (new B).retAmistake.asInstanceOf[A[_ <: A[_]]].retT.retT

        ???
    }

}
