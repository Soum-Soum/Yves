 
top right : 1209 - 406
top left : 699 - 304
buttom right : 1209 - 195
buttom left : 699 - 195




if (w.montantsBeforCut.get("midLeftMontant") != null){
                midleftUnderBeam = w.montantsBeforCut.get("midLeftMontant").buttomLeft.isUnderObstacle(beams,windows);
            }
            if (w.montantsBeforCut.get("midRightMontant") != null){
                midrighttUnderBeam = w.montantsBeforCut.get("midRightMontant").buttomRight.isUnderObstacle(beams,windows);
            }
            if (leftUnderBeam && rightUnderBeam){
                try{w.montantsAfterCut.put("midRightMontant", handleCollision(w.montantsBeforCut.get("midRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("midLeftMontant", handleCollision(w.montantsBeforCut.get("midLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomLeftMontant", handleCollision(w.montantsBeforCut.get("buttomLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomRightMontant", handleCollision(w.montantsBeforCut.get("buttomRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
            }else if (leftUnderBeam){
                try{w.montantsAfterCut.put("buttomLeftMontant", handleCollision(w.montantsBeforCut.get("buttomLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomRightMontant", handleCollision(w.montantsBeforCut.get("buttomRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("midRightMontant", handleCollision(w.montantsBeforCut.get("midRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("midLeftMontant", handleCollision(w.montantsBeforCut.get("midLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                if (midrighttUnderBeam!= null && !midrighttUnderBeam){
                    try{w.montantsAfterCut.put("rightMontant", handleCollision(w.montantsBeforCut.get("rightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                }else if (midrighttUnderBeam!= null && midrighttUnderBeam){
                    try{w.montantsAfterCut.put("rightMontant", handleCollision(w.montantsBeforCut.get("rightMontant"),false,true,CollisionBehaviour.STOP_LAST_BUTTOM));}catch (NullPointerException e){}
                }
            }else if (rightUnderBeam){
                if (midleftUnderBeam!= null && !midleftUnderBeam){
                    try{w.montantsAfterCut.put("leftMontant", handleCollision(w.montantsBeforCut.get("leftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                }else if (midleftUnderBeam!= null && midleftUnderBeam){
                    try{w.montantsAfterCut.put("leftMontant", handleCollision(w.montantsBeforCut.get("leftMontant"),false,true,CollisionBehaviour.STOP_LAST_BUTTOM));}catch (NullPointerException e){}
                }
                try{w.montantsAfterCut.put("midRightMontant", handleCollision(w.montantsBeforCut.get("midRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("midLeftMontant", handleCollision(w.montantsBeforCut.get("midLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomLeftMontant", handleCollision(w.montantsBeforCut.get("buttomLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomRightMontant", handleCollision(w.montantsBeforCut.get("buttomRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
            }else {

                if (midleftUnderBeam!= null && !midleftUnderBeam){
                    try{w.montantsAfterCut.put("leftMontant", handleCollision(w.montantsBeforCut.get("leftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                }else if (midleftUnderBeam!= null && midleftUnderBeam){
                    try{w.montantsAfterCut.put("leftMontant", handleCollision(w.montantsBeforCut.get("leftMontant"),false,true,CollisionBehaviour.STOP_LAST_BUTTOM));}catch (NullPointerException e){}
                }
                try{w.montantsAfterCut.put("midLeftMontant", handleCollision(w.montantsBeforCut.get("midLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomLeftMontant", handleCollision(w.montantsBeforCut.get("buttomLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomRightMontant", handleCollision(w.montantsBeforCut.get("buttomRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("midRightMontant", handleCollision(w.montantsBeforCut.get("midRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                if (midrighttUnderBeam!= null && !midrighttUnderBeam){
                    try{w.montantsAfterCut.put("rightMontant", handleCollision(w.montantsBeforCut.get("rightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                }else if (midrighttUnderBeam!= null && midrighttUnderBeam){
                    try{w.montantsAfterCut.put("rightMontant", handleCollision(w.montantsBeforCut.get("rightMontant"),false,true,CollisionBehaviour.STOP_LAST_BUTTOM));}catch (NullPointerException e){}
                }
            }