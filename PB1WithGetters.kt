import kotlin.Exception
import kotlin.math.min

fun valueException():Int{
    throw Exception("Invalide Uhrzeit")
}
fun main(){
    val entryTime = Time(12,0)
    val ticket = ParkTicket(entryTime)
    ticket.checkout(Time(12,39))
    println(ticket.parkingDuration)
    println(ticket.hoursStarted)
    //println(ticket.parkingDuration())
    //println(ticket.hoursStarted())

    val ticket2 = ParkTicket(Time(12,30))
    ticket2.checkout(Time(13,40))
    println(ticket2.parkingDuration)
    println(ticket2.hoursStarted)
    //println(ticket2.parkingDuration())
    //println(ticket2.hoursStarted())

    }
class ParkTicket(KentryTime : Time){
     private val entryTime = KentryTime
     private var nexitTime: Time? = null


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

