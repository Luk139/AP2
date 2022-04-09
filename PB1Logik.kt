import kotlin.Exception

enum class Parktarif{
    STANDARD,EVENT,WEEKEND;

    fun price() : Double{
        val x : Double = when(this){
            STANDARD -> 1.99
            EVENT -> 1.49
            WEEKEND -> 2.99
        }
        return x
    }
}

fun valueException():Int{
    throw Exception("Invalide Uhrzeit")
}
fun main(){
    val machine = ParkAutomat(Parktarif.STANDARD)
    val ticket1 = machine.generate(Time(12,0))
    val ticket2 = machine.generate(Time(12,30))
    val ticket3 = machine.generate(Time(13,30))
    val ticket4 = machine.generate(Time(13,30))

    ticket1.checkout(Time(12,30))
    ticket2.checkout(Time(13,30))
    ticket3.checkout(Time(14,50))
    println(machine.shortestParkingDuration())
//    println(machine.averageParkingDuration())
    println(machine.revenues())
    //val entryTime = Time(12,0)
    //val ticket = ParkTicket(entryTime)
    //ticket.checkout(Time(12,39))
    //val standart = Parktarif.STANDARD
    //val event = Parktarif.EVENT
    //val weekend = Parktarif.WEEKEND
    //println(standart.price())
    //println(weekend.price())
    //println(ticket.parkingDuration)
    //println(ticket.hoursStarted)
    //println(ticket.parkingDuration())
    //println(ticket.hoursStarted())

    //val ticket2 = ParkTicket(Time(12,30))
    //ticket2.checkout(Time(13,40))
    //println(ticket2.parkingDuration)
    //println(ticket2.hoursStarted)
    //println(ticket2.parkingDuration())
    //println(ticket2.hoursStarted())

    }
class ParkTicket(KentryTime : Time){
     private val entryTime = KentryTime
     public var nexitTime: Time? = null


   fun checkout(exitTime : Time){
        if(!exitTime.timeIsValid(entryTime,exitTime)){
            valueException()
        }
        nexitTime = exitTime

    }

    val parkingDuration
        get() : Int{
        var x : Int
        x =0
        val minuteCalc = nexitTime!!.minuteToInt()-entryTime.minuteToInt()
        val hourCalc = (nexitTime!!.hourToInt()-entryTime.hourToInt())*60
        //00 00
        if(hourCalc==0&&minuteCalc==0){
            x = 0
        }
        //01 00
        else if(hourCalc>0&&minuteCalc==0){
            x = hourCalc
        }
        //01 01
        else if(hourCalc>0&&minuteCalc>0){
            x = hourCalc+minuteCalc
        }
        //00 01
        else if(hourCalc==0&&minuteCalc>0){
            x = minuteCalc
        }
        //01 01
        else if(hourCalc>0){       //&&minuteCalc<0 theoretisch steht dies in der klammer,
            // da minutecalc<0 hier aber true sein muss, muss dies nicht unbedingt rein
            //(laut kotlin)
            x = hourCalc+minuteCalc*-1
        }

        return x
    }
    val hoursStarted
        get() : Int{
            var x : Int
            x = 1
            if(nexitTime!!.hourToInt()-entryTime.hourToInt()==0){
                x = 1
            }
            else if(nexitTime!!.minuteToInt()-entryTime.minuteToInt()>0){
                x = nexitTime!!.hourToInt()-entryTime.hourToInt()+1
            }
            return x

        }

    //THIS WORKS
    /* fun parkingDuration() : Int{
         var x : Int
         x =0
         var minuteCalc = nexitTime!!.minuteToInt()-entryTime.minuteToInt()
         var hourCalc = (nexitTime!!.hourToInt()-entryTime.hourToInt())*60
         //00 00
         if(hourCalc==0&&minuteCalc==0){
             x = 0
         }
         //01 00
         else if(hourCalc>0&&minuteCalc==0){
             x = hourCalc
         }
         //01 01
         else if(hourCalc>0&&minuteCalc>0){
             x = hourCalc+minuteCalc
         }
         //00 01
         else if(hourCalc==0&&minuteCalc>0){
             x = minuteCalc
         }
         //01 01
         else if(hourCalc>0){       //&&minuteCalc<0 theoretisch steht dies in der klammer,
                                    // da minutecalc<0 hier aber true sein muss, muss dies nicht unbedingt rein
                                    //(laut kotlin)
             x = hourCalc+minuteCalc*-1
         }

         return x

    }*/
    //This works too
   /* fun hoursStarted() : Int{
        var x : Int
        x = 1
        if(nexitTime!!.hourToInt()-entryTime.hourToInt()==0){
            x = 1
        }
        else if(nexitTime!!.minuteToInt()-entryTime.minuteToInt()>0){
            x = nexitTime!!.hourToInt()-entryTime.hourToInt()+1
        }
        return x
    }*/


}

class Time(Hour : Int  , Minute : Int = 0){
    private var hour = Hour
    private var minute = Minute

    init{
        if (hour >24||hour<0) {
            valueException()
        }
        else if(minute>60||minute<0){
            valueException()
        }
    }

    fun hourToInt(): Int {
        return hour
    }
    fun minuteToInt(): Int {
        return minute
    }
    fun timeIsValid(entryTime : Time, exitTime: Time) : Boolean{
        var x : Boolean
        x = true
        if(entryTime.hourToInt()==exitTime.hourToInt()&&entryTime.minuteToInt()>exitTime.minute)
            x = false
        else if(entryTime.hourToInt()<exitTime.hourToInt())
            x = true
        else if(entryTime.hourToInt()>exitTime.hourToInt())
            x = false
        else if(entryTime.hourToInt()==exitTime.hourToInt()&&entryTime.minuteToInt()<exitTime.minuteToInt()){
            x = true}
        return x
    }
}

class ParkAutomat(tarif : Parktarif){
    var parkscheine = listOf<ParkTicket>()
    var validParkscheine = listOf<ParkTicket>()
    var akTarif =tarif

    fun generate(entryTime: Time): ParkTicket {
       parkscheine.toMutableList().add(ParkTicket(entryTime))
        return ParkTicket(entryTime)
    }
    fun validTicket(ticket : ParkTicket){
        val c : Boolean = ticket.nexitTime != null
        if(c){
           validParkscheine.toMutableList().add(ticket)
        }
    }
    fun shortestParkingDuration (): Int{
        var x : Int = 0
        var temp : Int
        var y : Int = 0

        for (i in validParkscheine.indices) {
            x = validParkscheine[i].parkingDuration
            temp = validParkscheine[i+1].parkingDuration

            if(x>temp){
                y = temp
            }
            else
                y = x
        }
        return y
    }
    fun averageParkingDuration(): Int {
        var sum: Int = 0
        var y : Int = 0
        for (i in validParkscheine.indices) {
            sum += validParkscheine[i].parkingDuration
        }
        y = sum / validParkscheine.size
        return y
    }
    fun revenues () : Double{
        var sum: Int = 0
        var x:Double =0.0
        for (i in validParkscheine.indices) {
            sum += validParkscheine[i].hoursStarted
        }
        x = this.akTarif.price()*sum
        return x
    }

}



